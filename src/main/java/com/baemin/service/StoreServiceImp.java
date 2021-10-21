package com.baemin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baemin.dao.StoreDAO;
import com.baemin.vo.Food;
import com.baemin.vo.Review;
import com.baemin.vo.Store;

@Service
public class StoreServiceImp implements StoreService {

	@Autowired
	private StoreDAO storeDAO;

	@Override
	public List<Store> storeList(int category, int address1) {
		return storeDAO.storeList(category, address1);
	}

	@Transactional
	@Override
	public Map<String, Object> storeDetail(int id) {
		
		Map<String, Object> map = new HashMap<>();
		
		
		Store storeInfo = storeDAO.storeDetail(id); 
		List<Food> foodList = storeDAO.foodList(id);
		List<Review> reviewList = storeDAO.reviewList(id);
		
		System.out.println(foodList);
		
		map.put("storeInfo", storeInfo);
		map.put("foodList", foodList);
		map.put("reviewList", reviewList);
		
		return map;
	}

	@Override
	public List<String> foodOption(int foodId) {
		return storeDAO.foodOption(foodId);
	}

	@Override
	public void reviewWrite(Review review) {
		storeDAO.reviewWrite(review);
		
	}
	
	

}
