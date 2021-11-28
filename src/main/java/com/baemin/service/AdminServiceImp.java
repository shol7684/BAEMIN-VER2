package com.baemin.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baemin.dao.AdminDAO;
import com.baemin.util.Page;
import com.baemin.vo.Food;
import com.baemin.vo.OrderList;
import com.baemin.vo.Sales;
import com.baemin.vo.Store;

@Service
public class AdminServiceImp implements AdminService {

	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private PaymentService payment;
	
	
	@Override
	public List<OrderList> orderList(String list, Optional<Integer> page) {
		Page p = new Page(page);
		return adminDAO.orderList(list,p);
	}
	

	@Override
	public List<Store> storeList(Page p) {
		return adminDAO.storeList(p, null);
	}
	
	@Override
	public List<Store> storeList(Page p, String keyword) {
		return  adminDAO.storeList(p, keyword);
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
	public int orderAccept(String orderNum, int time,long userId) {
		return adminDAO.orderAccept(orderNum, time,userId);
	}
	
	@Override
	public int orderCancle(OrderList orderList, String cancleReason) throws IOException, ParseException {
		if(!orderList.getImpUid().equals("")) {
			String token = payment.getToken(); 
			int price = orderList.getTotalPrice();
			int usedPoint = orderList.getUsedPoint();
			int deleveryTip = orderList.getDeleveryTip();
			int refundPrice = price + deleveryTip - usedPoint;
			
			payment.payMentCancle(token, orderList.getImpUid(), refundPrice+"", cancleReason);
		}
		
		return adminDAO.orderCancle(orderList.getOrderNum(), cancleReason, orderList.getUserId());
	}
	
	@Override
	public int orderComplete(String orderNum, long userId) {
		return adminDAO.orderComplete(orderNum, userId);
	}
	

	@Override
	public Map<String, Object> pointRegist(String giftCardNum, long id) {
		
		Map<String, Object> giftCard = adminDAO.selectCard(giftCardNum);
		
		System.out.println("giftCart = " + giftCard);
		if(giftCard != null) {
			String info = giftCard.get("info").toString();
			int point = Integer.parseInt(giftCard.get("point").toString());
			
			adminDAO.pointUpdate(id, info, point );
			return giftCard;
		}
		
 		return null;
	}

	@Override
	public List<Sales> sales(String time,String month) {
		
		if(time.equals("year")) {
			List<Sales> year = adminDAO.salesYear();
			
			return year;
		}
		
		return adminDAO.sales(time,month);
	}

	@Transactional
	@Override
	public void modifyMenu(Food food, String[] foodOption, Integer[] foodOptionPrice, Integer[] optionId) {
		adminDAO.modifyMenu(food);
		
		if(foodOption == null) {
			return;
		}
		
		for(int i=0,j=0;i<foodOption.length;i++) {
			if(j < optionId.length && optionId[j] != null) {
				Map<String, Object> map = new HashMap<>();
				
				map.put("optionId", optionId[j]);
				map.put("foodOption", foodOption[i]);
				map.put("foodOptionPrice", foodOptionPrice[i]);
				adminDAO.modifyMenuOption(map);
				j++;
				
			} else {
				Map<String, Object> map = new HashMap<>();
				
				map.put("foodOption", foodOption[i]);
				map.put("foodOptionPrice", foodOptionPrice[i]);
				map.put("foodId", food.getId());
				adminDAO.addMenuOption(map);
			}
		}
		
	}

	
	
	@Override
	public void deleteOption(long optionId) {
		adminDAO.deleteOPtion(optionId);
	}







	


}
