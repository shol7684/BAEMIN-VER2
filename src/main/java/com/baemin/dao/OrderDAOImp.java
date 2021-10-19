package com.baemin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baemin.vo.Cart;

@Repository
public class OrderDAOImp implements OrderDAO {

	@Autowired
	private SqlSession sql;

	@Override
	public List<Long> foodPriceList(List<Cart> cartList) {
		return sql.selectList("order.foodPriceList" , cartList);
	}

	@Override
	public List<Integer> optionPriceList(List<Cart> cartList) {
		return sql.selectList("order.optionPriceList" , cartList);
	}

	@Override
	public void nonUserPayment(Map cartMap) {
		sql.insert("order.nonUserPayment" , cartMap);
	}

	@Override
	public void userPayment(Map cartMap) {
		sql.insert("order.userPayment" , cartMap);
		
	}

	

}
