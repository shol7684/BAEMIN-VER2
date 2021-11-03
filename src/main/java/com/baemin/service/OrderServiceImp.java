package com.baemin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public long orderPriceCheck(Map cartMap) {

		System.out.println("cartMap = " + cartMap);

//		List<Integer> cartId = (List<Integer>) cartMap.get("cartId");
		List<Cart> cartList = (List<Cart>) cartMap.get("cartList");
		List<Integer> amountList = (List<Integer>) cartMap.get("amountList");
//		List<Long> totalPriceList =(List<Long>) cartMap.get("totalPriceList");
//		int deleveryTip = (int) cartMap.get("deleveryTip");
		int deleveryTip = orderDAO.getDeleveryTip((int) cartMap.get("storeId"));

		List<Long> foodPriceList = orderDAO.foodPriceList(cartList);
		List<Integer> optionPriceList = orderDAO.optionPriceList(cartList);

		long sum = 0;

		for (int i = 0; i < cartList.size(); i++) {
			long foodPrice = foodPriceList.get(i);
			int amount = amountList.get(i);
			int optionPrice = optionPriceList.get(i);

			sum += (foodPrice + optionPrice) * amount;
		}

		return sum + deleveryTip;
	}

	@Transactional
	@Override
	public String order(Map cartMap, OrderInfo info, LoginDetail user, HttpSession session) {
		
		String orderNum = CreateOrderNum.createOrderNum();
		
		info.setOrderNum(orderNum);
		long userId = 0;
		if (user != null) {
			userId = user.getUser().getId();
			info.setUserId(userId);
		}
		

		Map<String, String> orderDetail = new HashMap<>();
		Gson gson = new Gson();

		String amount = cartMap.get("amountList").toString();
		String foodPrice = cartMap.get("totalPriceList").toString();
		String cartList = gson.toJson(cartMap.get("cartList"));
			   cartList = cartList.substring(1, cartList.length()-1); 
		String totalPrice = cartMap.get("menuTotalPrice").toString();
		String storeId = cartMap.get("storeId").toString();
		
		orderDetail.put("orderNum", orderNum);
		orderDetail.put("amount", amount);
		orderDetail.put("foodPrice", foodPrice);
		orderDetail.put("totalPrice", totalPrice);
		orderDetail.put("foodInfo", cartList);
		orderDetail.put("storeId", storeId);
		orderDetail.put("usedPoint", info.getUsedPoint() + "");
		orderDetail.put("request", info.getRequest());
		orderDetail.put("userId", userId + "");
		
		LOGGER.info("사용 포인트 = " + info.getUsedPoint());

		orderDAO.order(info);
		orderDAO.orderDetail(orderDetail);
		
		
		// 로그인 사용자가 포인트 사용했을때
		if(info.getUsedPoint() != 0 ) {
			String storeName =  cartMap.get("storeName").toString();
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
			String storeName =  cartMap.get("storeName").toString();
			int point = Integer.parseInt(totalPrice);
				point = (int) (point * 0.01);
			
			int result = adminDAO.pointUpdate(userId, storeName, point);
			
			if(result == 1) {
				UserInfoSessionUpdate.sessionUpdate(point+"", "point", user, session);
			}
		}
		
		
		return orderNum;
	}

	@Override
	public List<OrderList> orderList(long userId, int startPage,int endPage) {
		return orderDAO.orderList(userId, startPage, endPage);
	}

	
	@Override
	public OrderList orderListDetail(String orderNum) {
		return orderDAO.orderListDetail(orderNum);
	}

}
