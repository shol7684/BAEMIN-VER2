package com.baemin.service;

import java.util.List;
import java.util.Map;

import com.baemin.vo.Food;
import com.baemin.vo.OrderList;
import com.baemin.vo.Store;

public interface AdminService {

	List<OrderList> orderList();

	List<Store> storeList();

	void storeRegist(Store store);

	String bossComment(String orderNum, String bossComment);

	void menuDelete(int storeId, long[] deleteNumber);

	void addMenu(Food food, String[] foodOption, Integer[] foodOptionPrice);

}
