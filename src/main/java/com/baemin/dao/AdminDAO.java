package com.baemin.dao;

import java.util.List;
import java.util.Map;

import com.baemin.util.Page;
import com.baemin.vo.Food;
import com.baemin.vo.OrderList;
import com.baemin.vo.Sales;
import com.baemin.vo.Store;

public interface AdminDAO {

	List<OrderList> orderList(String list, Page p);

//	List<Store> storeList(Page p);
	List<Store> storeList(Page p, String keyword);

	void storeRegist(Store store);

	void bossComment(String orderNum, String bossComment);

	void menuDelete(int storeId, long[] deleteNumber);

	long addMenu(Food food);

	void addMenuOption(Map map);

	void storeModify(Store store);

	int orderAccept(String orderNum, int time, long userId);
	
	int orderCancle(String orderNum, String cancleReason, long userId);

	Map<String, Object> selectCard(String giftCardNum);

	int pointUpdate(long userId, String info, int point);

	int orderComplete(String orderNum, long userId);

	List<Sales> salesYear();

	List<Sales> sales(String time, String month);

	void modifyMenu(Food food);

	void modifyMenuOption(Map<String, Object> map);

	void deleteOPtion(long optionId);




}
