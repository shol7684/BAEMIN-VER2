package com.baemin.vo;

import java.util.Arrays;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cart {
	private long foodId;
	private String foodName;
	private long foodPrice;
//	private long amount;
//	private long totalPrice;

	private String[] foodOptionName;
	private int[] foodOptionPrice;
	private long[] foodOptionId;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(foodOptionId);
		result = prime * result + Arrays.hashCode(foodOptionName);
		result = prime * result + Arrays.hashCode(foodOptionPrice);
		result = prime * result + Objects.hash(foodId, foodName, foodPrice);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		return foodId == other.foodId && Objects.equals(foodName, other.foodName)
				&& Arrays.equals(foodOptionId, other.foodOptionId)
				&& Arrays.equals(foodOptionName, other.foodOptionName)
				&& Arrays.equals(foodOptionPrice, other.foodOptionPrice) && foodPrice == other.foodPrice;
	}
	
	
	

	
}
