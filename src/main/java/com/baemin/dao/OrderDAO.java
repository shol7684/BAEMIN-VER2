package com.baemin.dao;

import java.util.List;
import java.util.Map;

import com.baemin.vo.Cart;

public interface OrderDAO {

	List<Long> foodPriceList(List<Cart> cartList);

	List<Integer> optionPriceList(List<Cart> cartList);

	void nonUserPayment(Map cartMap);

	void userPayment(Map cartMap);

}
