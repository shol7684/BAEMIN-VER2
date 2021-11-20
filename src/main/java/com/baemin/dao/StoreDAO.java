package com.baemin.dao;

import java.util.List;
import java.util.Map;

import com.baemin.vo.Food;
import com.baemin.vo.FoodOption;
import com.baemin.vo.Review;
import com.baemin.vo.Store;

public interface StoreDAO {

	List<Store> storeList(Map map);

	Store storeDetail(long storeId);

	List<Food> foodList(long id);

	List<FoodOption> foodOption(int foodId);

	void reviewWrite(Review review);

	List<Review> reviewList(long id);

	void reviewModify(Review review);

	void addLikes(Map<String, Long> map);

	void deleteLikes(Map<String, Long> map);

	int isLikes(Map<String, Long> m);

	List<Store> likesList(long userId);

	List<Store> likesListNonUser(String likes);
	
	List<Store> storeSearch(int address1, String address2, int pageStart, int pageEnd);



}
