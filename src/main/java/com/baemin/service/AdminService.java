package com.baemin.service;

import java.util.List;
import java.util.Map;

import com.baemin.vo.OrderList;
import com.baemin.vo.Store;

public interface AdminService {

	List<OrderList> orderList();

	List<Store> storeList();

	void storeRegist(Store store);

	void bossComment(String orderNum, String bossComment);

}
