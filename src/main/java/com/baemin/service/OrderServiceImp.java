package com.baemin.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baemin.config.LoginDetail;
import com.baemin.dao.OrderDAO;
import com.baemin.vo.Cart;
import com.baemin.vo.OrderInfo;
import com.baemin.vo.OrderList;
import com.google.gson.Gson;

@Service
public class OrderServiceImp implements OrderService {

	@Autowired
	private OrderDAO orderDAO;

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
	public void order(Map cartMap, OrderInfo info, LoginDetail user) {
		Calendar cal = Calendar.getInstance();

		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1;
		int d = cal.get(Calendar.DATE);

		StringBuilder sb = new StringBuilder();
		sb.append(y).append(m).append(d);

		for (int i = 0; i < 10; i++) {
			int random = (int) (Math.random() * 10);
			sb.append(random);
		}

		Map<String, String> orderDetail = new HashMap<>();
		Gson gson = new Gson();

		String amount = cartMap.get("amountList").toString();
		String foodPrice = cartMap.get("totalPriceList").toString();
		String cartList = gson.toJson(cartMap.get("cartList"));
			   cartList = cartList.substring(1, cartList.length()-1); 
		String totalPrice = cartMap.get("menuTotalPrice").toString();
		String storeId = cartMap.get("storeId").toString();
		
		orderDetail.put("orderNum", sb.toString());
		orderDetail.put("amount", amount);
		orderDetail.put("foodPrice", foodPrice);
		orderDetail.put("totalPrice", totalPrice);
		orderDetail.put("foodInfo", cartList);
		orderDetail.put("storeId", storeId);
		orderDetail.put("usedPoint", info.getUsedPoint() + "");
		orderDetail.put("request", info.getRequest());
		
		
		info.setOrderNum(sb.toString());
		if (user != null) {
			long userId = user.getUser().getId();
			info.setUserId(userId);
			
		}
		System.out.println("사용 포인트 = " + info.getUsedPoint() );

		System.out.println("cartMap.get(\"cartList\") =" + cartMap.get("cartList"));
		System.out.println("orderDetail =" + orderDetail);
		orderDAO.order(info);
		orderDAO.orderDetail(orderDetail);
		
		
	}

	@Override
	public List<OrderList> orderList(long userId) {
		return orderDAO.orderList(userId);
	}

	
	@Override
	public OrderList orderListDetail(String orderNum) {
		return orderDAO.orderListDetail(orderNum);
	}

}
