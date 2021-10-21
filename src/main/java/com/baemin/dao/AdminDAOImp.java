package com.baemin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baemin.vo.OrderList;
import com.baemin.vo.Store;

@Repository
public class AdminDAOImp implements AdminDAO {

	@Autowired
	private SqlSession sql;

	@Override
	public List<OrderList> orderList() {
		return sql.selectList("admin.orderList");
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

}
