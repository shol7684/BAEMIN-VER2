package com.baemin.service;

import java.util.List;
import java.util.Map;

import com.baemin.vo.Review;
import com.baemin.vo.Store;

public interface StoreService {

	List<Store> storeList(int category, int address, int page);

	Map storeDetail(int id);

	List<String> foodOption(int foodId);

	void reviewWrite(Review review);

	void reviewModify(Review review);

	
}
