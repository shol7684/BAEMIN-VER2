package com.baemin.dao;

import java.util.List;
import java.util.Map;

import com.baemin.vo.Food;
import com.baemin.vo.Review;
import com.baemin.vo.Store;

public interface StoreDAO {

	List<Store> storeList(Map map);

	Store storeDetail(long storeId);

	List<Food> foodList(long id);

	List<String> foodOption(int foodId);

	void reviewWrite(Review review);

	List<Review> reviewList(long id);

	void reviewModify(Review review);

	void addLikes(Map<String, Long> map);

	void deleteLikes(Map<String, Long> map);

	Long isLikes(Map<String, Long> m);

	List likesList(long userId);


}
