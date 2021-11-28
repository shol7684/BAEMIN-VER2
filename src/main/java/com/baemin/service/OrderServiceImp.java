package com.baemin.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baemin.config.LoginDetail;
import com.baemin.dao.AdminDAO;
import com.baemin.dao.OrderDAO;
import com.baemin.util.CreateOrderNum;
import com.baemin.util.Page;
import com.baemin.util.UserInfoSessionUpdate;
import com.baemin.vo.Cart;
import com.baemin.vo.CartList;
import com.baemin.vo.OrderDetail;
import com.baemin.vo.OrderInfo;
import com.baemin.vo.OrderList;
import com.google.gson.Gson;

@Service
public class OrderServiceImp implements OrderService {

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private AdminDAO adminDAO;
	
	private static final Logger LOGGER = LogManager.getLogger(OrderServiceImp.class);
	
	@Transactional
	@Override
	public long orderPriceCheck(CartList cartList) {

		System.out.println("cartDetail = " + cartList);

		List<Cart> cart = cartList.getCart();
		List<Integer> foodPriceList = orderDAO.foodPriceList(cart);
		List<Integer> optionPriceList = orderDAO.optionPriceList(cart);
		int deleveryTip = orderDAO.getDeleveryTip(cartList.getStoreId());
		System.out.println("foodPriceList = " + foodPriceList);
		System.out.println("optionPriceList = " + optionPriceList);
		
		int sum = 0;
		
		for (int i = 0; i < cart.size(); i++) {
			int foodPrice = foodPriceList.get(i);
			int amount = cart.get(i).getAmount();
			int optionPrice = optionPriceList.get(i);

			sum += (foodPrice + optionPrice) * amount;
		}

		return sum + deleveryTip; 
	}

	@Transactional
	@Override
	public String order(CartList cart, OrderInfo info, LoginDetail user, HttpSession session) {
		Gson gson = new Gson();
		String orderNum = CreateOrderNum.createOrderNum();
		int total = cart.getCartTotal();
		
		info.setStoreId(cart.getStoreId());
		info.setOrderNum(orderNum);
		info.setTotalPrice(total);
		
		long userId = 0;
		if (user != null) {
			userId = user.getUser().getId();
			info.setUserId(userId);
		}
		
		List<Cart> cartList = cart.getCart();
		
		OrderDetail[] detail = new OrderDetail[cartList.size()];
		
		
		for(int i=0;i<detail.length;i++) {
			String cartJSON = gson.toJson(cartList.get(i));
			detail[i] = new OrderDetail(orderNum, cartJSON);
		}
		
		orderDAO.order(info);
		orderDAO.orderDetail(detail, userId);
		
		LOGGER.info("사용 포인트 = " + info.getUsedPoint());

		System.out.println("info2 = "+ info  );
		
		// 로그인 사용자가 포인트 사용했을때
		if(info.getUsedPoint() != 0 ) {
			String storeName =  cart.getStoreName();
			int usedPoint =  -info.getUsedPoint();
			int result = adminDAO.pointUpdate(userId, storeName, usedPoint);
			
			LOGGER.info("사용 매장 = "+ storeName);
			LOGGER.info("유저 아이디 = "+ userId);
			LOGGER.info("포인트 차감 " + usedPoint);
			
			if(result == 1) {
				UserInfoSessionUpdate.sessionUpdate(usedPoint+"", "point", user, session);
			}
		}
		
		
		// 회원 포인트 적립
		if (user != null) {
			String storeName = cart.getStoreName();
			int point = (int)(total * 0.01); 
			int result = adminDAO.pointUpdate(userId, storeName, point);
			if(result == 1) {
				UserInfoSessionUpdate.sessionUpdate(point+"", "point", user, session);
			}
		}
		
		
		return orderNum;
	}
	
	
	@Override
	public List<OrderList> orderList(long userId, Page p) {
		
		return orderDAO.orderList(userId, p.getPageStart(), p.getPageEnd());
	}

	
	@Override
	public OrderList orderListDetail(String orderNum) {
		return orderDAO.orderListDetail(orderNum);
	}

}
