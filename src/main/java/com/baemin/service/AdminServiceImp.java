package com.baemin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baemin.dao.AdminDAO;
import com.baemin.vo.Food;
import com.baemin.vo.OrderList;
import com.baemin.vo.Review;
import com.baemin.vo.Store;

@Service
public class AdminServiceImp implements AdminService {

	@Autowired
	private AdminDAO adminDAO;
	
	@Override
	public List<OrderList> orderList() {
		return adminDAO.orderList();
	}

	@Override
	public List<Store> storeList() {
		return adminDAO.storeList();
	}

	@Override
	public void storeRegist(Store store) {
		adminDAO.storeRegist(store);
	}

	@Override
	public void bossComment(String orderNum, String bossComment) {
		adminDAO.bossComment(orderNum, bossComment);
		
	}


}
