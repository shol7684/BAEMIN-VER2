package com.baemin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baemin.util.Page;
import com.baemin.vo.Food;
import com.baemin.vo.OrderList;
import com.baemin.vo.Sales;
import com.baemin.vo.Store;

@Repository
public class AdminDAOImp implements AdminDAO {

	@Autowired
	private SqlSession sql;

	@Override
	public List<OrderList> orderList(String list, Page p) {
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("pageStart", p.getPageStart());
		map.put("pageEnd", p.getPageEnd());
		return sql.selectList("admin.orderList", map);
	}

	@Override
	public List<Store> storeList(Page p, String keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("p", p);
		map.put("keyword", keyword);
		
		System.out.println("p = " + p);
		System.out.println("keyword = " + keyword);
		
		return sql.selectList("admin.storeList", map);
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
	public void addMenuOption(Map map) {
		sql.insert("admin.addMenuOption", map);

	}

	@Override
	public void storeModify(Store store) {
		sql.update("admin.storeModify" , store);
	}

	@Override
	public int orderAccept(String orderNum, int time, long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNum", orderNum);
		map.put("time", time);
		map.put("userId", userId);
		
		System.out.println("map = " + map);
		return sql.update("admin.orderAccept", map);
	}
	
	@Override
	public int orderCancle(String orderNum, String cancleReason, long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNum", orderNum);
		map.put("cancleReason", "주문거부("+ cancleReason +")");
		map.put("userId", userId);
		
		return sql.update("admin.orderCancle", map);
	}
	
	@Override
	public int orderComplete(String orderNum, long userId) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNum", orderNum);
		map.put("userId", userId);
		
		return sql.update("admin.orderComplete", map);
	}
	

	@Override
	public Map<String, Object> selectCard(String giftCardNum) {
		return sql.selectOne("admin.selectCard", giftCardNum);
	}

	@Override
	public int pointUpdate(long userId, String info, int point) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("info", info);
		map.put("point", point);
		
		return sql.insert("admin.pointUpdate", map); 
	}

	@Override
	public List<Sales> salesYear() {
		return sql.selectList("admin.salesYear");
	}

	@Override
	public List<Sales> sales(String time, String month) {
		Map<String, Object> map = new HashMap<>();
		map.put("time", time);
		map.put("month", month);
		return sql.selectList("admin.salesMonth", map);
	}

	@Override
	public void modifyMenu(Food food) {
		sql.update("admin.modifyMenu", food);
	}

	@Override
	public void modifyMenuOption(Map<String, Object> map) {
		sql.update("admin.modifyMenuOption", map);
	}

	@Override
	public void deleteOPtion(long optionId) {
		sql.delete("admin.deleteOption", optionId);
	}



	
}
