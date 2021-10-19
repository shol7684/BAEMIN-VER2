package com.baemin.util;

import com.baemin.vo.Cart;

public class FoodPriceCalc {

	public static long foodPriceCalc(Cart cart,long amount) {
		
		int[] optionPrice = cart.getFoodOptionPrice();
		
		int optionPriceTotal = 0;
		if(optionPrice != null) {
			for(int i=0;i<optionPrice.length;i++) {
				optionPriceTotal += optionPrice[i];
			}
		}
		
		long foodPrice = cart.getFoodPrice() * amount;
		
		return foodPrice + (optionPriceTotal * amount);
	}
}
