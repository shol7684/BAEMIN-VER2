package com.baemin.util;

import java.util.HashMap;
import java.util.Map;

import com.baemin.vo.Cart;
import com.baemin.vo.OrderList;
import com.google.gson.Gson;

public class FoodInfoFromJson {

	public static Map<String, Object> foodInfoFromJson(OrderList orderListDetail) {
		
		Map<String, Object>  map = new HashMap<>();
		
		String foodInfo = orderListDetail.getFoodInfo();
		
		String[] foodArr = foodInfo.split("},");
		
		Cart[] cart = new Cart[foodArr.length];
		
		Gson gson = new Gson();
		
		for(int i=0;i<foodArr.length;i++) {
			if(!foodArr[i].endsWith("}")) {
				cart[i] = gson.fromJson(foodArr[i] + "}" , Cart.class);
			} else {
				cart[i] = gson.fromJson(foodArr[i], Cart.class);
			}
		}
		
		int[] amount = gson.fromJson(orderListDetail.getAmount(), int[].class);
		
		map.put("orderListDetail" , orderListDetail);
		map.put("cart" , cart);
		map.put("amount" , amount);
		
		return map;
	}
	
}
