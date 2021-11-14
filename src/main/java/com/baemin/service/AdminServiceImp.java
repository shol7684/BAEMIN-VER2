package com.baemin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Override
	public List<OrderList> orderList(String list) {
		
		return orderList(list, 1);
	}
	
	@Override
	public List<OrderList> orderList(String list, int page) {
		Page p = new Page(page, 20);
		
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
	public int orderCancle(String orderNum, String cancleReason, long userId) {
		return adminDAO.orderCancle(orderNum, cancleReason, userId);
	}
	
	@Override
	public int orderComplete(String orderNum, long userId) {
		return adminDAO.orderComplete(orderNum, userId);
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
	public List<Sales> sales(String time,String month) {
		
		if(time.equals("year")) {
			List<Sales> year = adminDAO.salesYear();
			
			return year;
			
			
//			Map<String, Long>  year = adminDAO.salesYear();
//			List<Sales> list = new ArrayList<>();
//			String[] yearArr = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOB", "DEC"};
//			
//			for(int i=0;i<12;i++) {
//				list.add(new Sales(null, Long.parseLong(String.valueOf(year.get(yearArr[i])))));
//			}
//			list.add(new Sales(null, Long.parseLong(String.valueOf(year.get("TOTAL")))));
//			return list;
		}
		
		return adminDAO.sales(time,month);
	}







	


}
