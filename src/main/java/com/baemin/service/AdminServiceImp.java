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
	public List<OrderList> orderList(String list) {
		return adminDAO.orderList(list);
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
		if(foodOption != null) {
			for(int i=0;i<foodOption.length;i++) {
				Map<String, Object> map = new HashMap<>();
				
				map.put("foodOption", foodOption[i]);
				map.put("foodOptionPrice", foodOptionPrice[i]);
				map.put("foodId", foodId);
				adminDAO.addMenuOption(map);
			}
		}
	}

	
	@Override
	public void storeModify(Store store) {
		adminDAO.storeModify(store);
		
	}

	@Override
	public void orderAccept(String orderNum, int time,long userId) {
		adminDAO.orderAccept(orderNum, time,userId);
		
	}

	@Override
	public int pointRegist(String giftCardNum, long id) {
		
		Map giftCard = adminDAO.selectCard(giftCardNum);
		
		if(giftCard != null) {
			String info = giftCard.get("info").toString();
			int point = Integer.parseInt(giftCard.get("point").toString());
			
			adminDAO.pointUpdate(id, info, point );
			return point;
		}
		
 		return 0;
	}

	@Override
	public OrderList getOrderOne(String orderNum) {
		return adminDAO.getOrderOne(orderNum);
	}


}
