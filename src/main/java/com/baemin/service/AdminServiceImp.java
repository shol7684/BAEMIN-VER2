package com.baemin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public String bossComment(String orderNum, String bossComment) {
		
		bossComment = bossComment.replace("\n","<br>").replaceAll(" ", "&nbsp");
		
		adminDAO.bossComment(orderNum, bossComment);
		
		return bossComment; 
	}

	@Override
	public void menuDelete(int storeId, long[] deleteNumber) {
		adminDAO.menuDelete(storeId, deleteNumber);
	}

	@Transactional
	@Override
	public void addMenu(Food food, String[] foodOption, Integer[] foodOptionPrice) {

		long foodId = adminDAO.addMenu(food);
		
		System.out.println("food Id = "  + foodId);
		
//		if(foodOption.length != 0) {
//			Map<String, Object> map = new HashMap<>();
//			map.put("foodOption", foodOption);
//			map.put("foodOptionPrice", foodOptionPrice);
//			map.put("foodId", foodId);
//			adminDAO.addMenuOption(map);
//		}
		
		for(int i=0;i<foodOption.length;i++) {
			Map<String, Object> map = new HashMap<>();
			
			if(foodOptionPrice[i] == null ) {
				foodOptionPrice[i] = 0;
			}
			
			map.put("foodOption", foodOption[i]);
			map.put("foodOptionPrice", foodOptionPrice[i]);
			map.put("foodId", foodId);
			adminDAO.addMenuOption(map);
		}
		
		
	}


}
