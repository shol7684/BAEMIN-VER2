package com.baemin.dao;

import java.util.List;

import com.baemin.vo.OrderList;
import com.baemin.vo.Store;

public interface AdminDAO {

	List<OrderList> orderList();

	List<Store> storeList();

	void storeRegist(Store store);

	void bossComment(String orderNum, String bossComment);

}
