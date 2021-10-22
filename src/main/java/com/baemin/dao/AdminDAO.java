package com.baemin.dao;

import java.util.List;
import java.util.Map;

import com.baemin.vo.Food;
import com.baemin.vo.OrderList;
import com.baemin.vo.Store;

public interface AdminDAO {

	List<OrderList> orderList();

	List<Store> storeList();

	void storeRegist(Store store);

	void bossComment(String orderNum, String bossComment);

	void menuDelete(int storeId, long[] deleteNumber);

	long addMenu(Food food);

	void addMenuOption(Map<String, Object> map);

}
