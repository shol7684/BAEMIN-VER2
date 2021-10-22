package com.baemin.dao;

import java.util.List;
import java.util.Map;

import com.baemin.vo.Food;
import com.baemin.vo.Review;
import com.baemin.vo.Store;

public interface StoreDAO {

	List<Store> storeList(Map map);

	Store storeDetail(int id);

	List<Food> foodList(int id);

	List<String> foodOption(int foodId);

	void reviewWrite(Review review);

	List<Review> reviewList(int id);

	void reviewModify(Review review);


}
