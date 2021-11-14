package com.baemin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baemin.dao.StoreDAO;
import com.baemin.util.Page;
import com.baemin.vo.Food;
import com.baemin.vo.FoodOption;
import com.baemin.vo.Review;
import com.baemin.vo.Store;
import com.baemin.vo.StoreDetail;

@Service
public class StoreServiceImp implements StoreService {

	@Autowired
	private StoreDAO storeDAO;

	@Override
	public List<Store> storeList(int category, int address1, int page) {
		
		Page p = new Page(page, 20);
		
		Map<String, Object> map = new HashMap<>();

		map.put("category", category);
		map.put("address1", address1);
		map.put("startPage", p.getPageStart());
		map.put("endPage", p.getPageEnd());
		
		
		return storeDAO.storeList(map);
	}
	
	@Override
	public List<Store> storeList(int category, int address1, String sort, int page) {
		Page p = new Page(page, 20);
		
		Map<String, Object> map = new HashMap<>();

		map.put("category", category);
		map.put("address1", address1);
		map.put("startPage", p.getPageStart());
		map.put("endPage", p.getPageEnd());
		map.put("sort", sort);
		
		return storeDAO.storeList(map);
	}
	

	@Transactional
	@Override
	public StoreDetail storeDetail(long storeId, long userId) throws NotFoundException {
		
		Store storeInfo = storeDAO.storeDetail(storeId); 
		if(storeInfo == null) {
			throw new NotFoundException("없는 페이지");
		}
		List<Food> foodList = storeDAO.foodList(storeId);
		List<Review> reviewList = storeDAO.reviewList(storeId);
		
		if(userId != 0) {
			Map<String, Long> m = new HashMap<>();
			m.put("storeId" , storeId);
			m.put("userId", userId);
			int isLikes = storeDAO.isLikes(m);
			
			if(isLikes != 0) {
				return new StoreDetail(storeInfo, foodList, reviewList, true);
			}
		}
		
		return new StoreDetail(storeInfo, foodList, reviewList, false);
	}

	@Override
	public List<FoodOption> foodOption(int foodId) {
		return storeDAO.foodOption(foodId);
	}

	@Override
	public void reviewWrite(Review review) {
		storeDAO.reviewWrite(review);
		
	}

	@Override
	public void reviewModify(Review review) {
		storeDAO.reviewModify(review);
		
	}

	@Override
	public void likes(long storeId, String likes, long userId) {
		Map<String, Long> map = new HashMap<>();
		map.put("storeId", storeId);
		map.put("userId", userId);
		
		if(likes.equals("on")) {
			storeDAO.addLikes(map);
		} else {
			storeDAO.deleteLikes(map);
		}
		
	}

	@Override
	public List likesList(long userId) {
		return storeDAO.likesList(userId);
	}

	@Override
	public List<Store> storeSearch(int address1, String keyword, Optional<Integer> movePage) {
		Page p = new Page(movePage);
		
		return storeDAO.storeSearch(address1, keyword, p.getPageStart(), p.getPageEnd());
	}


	
	

}
