package com.baemin.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartDetail {
	private List<Integer> cartId;
	private List<Cart> cartList;
	private List<Integer> amountList;
	private List<Long> totalPriceList;
	
	
	private long storeId;
	private long menuTotalPrice;
	private String storeName;
	private int deleveryTip;
	
	public CartDetail(List<Integer> cartId, List<Cart> cartList, List<Integer> amountList, List<Long> totalPriceList) {
		this.cartId = cartId;
		this.cartList = cartList;
		this.amountList = amountList;
		this.totalPriceList = totalPriceList;
	}
	
	
	
	
	
	
}
