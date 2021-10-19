package com.baemin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baemin.vo.Food;
import com.baemin.vo.Store;

@Repository
public class StoreDAOImp implements StoreDAO {
	
	@Autowired
	private SqlSession sql;

	@Override
	public List<Store> storeList(int category, int address1) {
		Map<String, Integer> map = new HashMap<>();
		
		map.put("category", category);
		map.put("address1", address1);
		
		return sql.selectList("store.storeList" , map);
	}

	@Override
	public Store storeDetail(int id) {
		return sql.selectOne("store.storeDetail" , id);
	}

	@Override
	public List<Food> foodList(int id) {
		return sql.selectList("store.foodList", id);
	}

	@Override
	public List<String> foodOption(int foodId) {
		return sql.selectList("store.foodOption" , foodId);
	}

}
