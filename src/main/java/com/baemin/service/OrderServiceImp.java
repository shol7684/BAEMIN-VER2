package com.baemin.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baemin.config.LoginDetail;
import com.baemin.dao.OrderDAO;
import com.baemin.vo.Cart;

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
		int deleveryTip = (int) cartMap.get("deleveryTip");

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

	
	@Override
	public void payment(Map cartMap, LoginDetail user) {
		
		Calendar cal = Calendar.getInstance();
		
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1; 
		int d = cal.get(Calendar.DATE);
		
		StringBuilder sb = new StringBuilder();
		sb.append(y).append(m).append(d);
		
		for(int i=0;i<10;i++) {
			int random = (int) (Math.random() * 10);
			sb.append(random);
		}
		
		cartMap.put("orderNum", sb);
		
		if(user == null) {
			orderDAO.nonUserPayment(cartMap);
		} else {
			orderDAO.userPayment(cartMap);
		}
	}

}
