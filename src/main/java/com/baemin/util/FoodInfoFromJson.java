package com.baemin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baemin.vo.Cart;
import com.google.gson.Gson;

public class FoodInfoFromJson {

	public static List<Cart> foodInfoFromJson(String foodInfoJSON) {
		Map<String, Object>  map = new HashMap<>();
//		System.out.println("json = " + foodInfoJSON);
		String[] arr = foodInfoJSON.split("/");
		Gson gson = new Gson();
		
		List<Cart> cartList = new ArrayList<>();
		for(int i=0;i<arr.length;i++) {
			cartList.add(gson.fromJson(arr[i], Cart.class));
		}
		return cartList;
	}
}
