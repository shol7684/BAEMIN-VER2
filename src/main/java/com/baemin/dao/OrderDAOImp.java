package com.baemin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baemin.vo.Cart;
import com.baemin.vo.OrderInfo;
import com.baemin.vo.OrderList;

@Repository
public class OrderDAOImp implements OrderDAO {

	@Autowired
	private SqlSession sql;

	@Override
	public int getDeleveryTip(int storeId) {
		return sql.selectOne("order.getDeleveryTip", storeId);
	}

	@Override
	public List<Long> foodPriceList(List<Cart> cartList) {
		return sql.selectList("order.foodPriceList", cartList);
	}

	@Override
	public List<Integer> optionPriceList(List<Cart> cartList) {
		return sql.selectList("order.optionPriceList", cartList);
	}

	@Override
	public void order(OrderInfo info) {
		sql.insert("order.order", info);
	}

	@Override
	public void orderDetail(Map<String, String> orderDetail) {
		sql.insert("order.orderDetail", orderDetail);
	}

	@Override
	public List<OrderList> orderList(long userId) {
		return sql.selectList("order.orderList", userId);
	}

	@Override
	public OrderList orderListDetail(String orderNum) {
		return sql.selectOne("order.orderListDetail", orderNum);
	}

	@Override
	public void updatePoint(long usedPoint, long userId, String storeName) {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("usedPoint", usedPoint);
		map.put("userId", userId);
		map.put("storeName", storeName);
		
		sql.update("order.updatePoint" , map);
	}

}
