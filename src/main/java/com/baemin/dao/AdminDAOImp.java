package com.baemin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baemin.vo.Food;
import com.baemin.vo.OrderList;
import com.baemin.vo.Store;

@Repository
public class AdminDAOImp implements AdminDAO {

	@Autowired
	private SqlSession sql;

	@Override
	public List<OrderList> orderList(String list) {
		return sql.selectList("admin.orderList", list);
	}

	@Override
	public List<Store> storeList() {
		return sql.selectList("admin.storeList");
	}

	@Override
	public void storeRegist(Store store) {
		sql.insert("admin.storeRegist", store);

	}

	@Override
	public void bossComment(String orderNum, String bossComment) {
		Map<String, String> map = new HashMap<>();

		map.put("bossComment", bossComment);
		map.put("orderNum", orderNum);

		sql.update("admin.bossComment", map);

	}

	@Override
	public void menuDelete(int storeId, long[] deleteNumber) {
		Map<String, Object> map = new HashMap<>();

		map.put("storeId", storeId);
		map.put("deleteNumber", deleteNumber);

		sql.delete("admin.menuDelete", map);
	}

	@Override
	public long addMenu(Food food) {
		sql.insert("admin.addMenu", food);
		
		System.out.println("food.getId() = " + food.getId()); 
		
		return food.getId();

	}

	@Override
	public void addMenuOption(Map<String, Object> map) {
		sql.insert("admin.addMenuOption", map);

	}

	@Override
	public void storeModify(Store store) {
		sql.update("admin.storeModify" , store);
	}

	@Override
	public void orderAccept(String orderNum, int time, long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNum", orderNum);
		map.put("time", time);
		map.put("userId", userId);
		sql.update("admin.orderAccept", map);
	}

	@Override
	public Map selectCard(String giftCardNum) {
		return sql.selectOne("admin.selectCard", giftCardNum);
	}

	@Override
	public int pointUpdate(long userId, String info, int point) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("info", info);
		map.put("point", point);
		
		return sql.insert("admin.pointUpdate", map); 
	}

	@Override
	public OrderList getOrderOne(String orderNum) {
		return sql.selectOne("admin.getOrderOne", orderNum);
	}
}
