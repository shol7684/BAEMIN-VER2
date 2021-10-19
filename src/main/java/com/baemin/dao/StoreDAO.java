package com.baemin.dao;

import java.util.List;

import com.baemin.vo.Food;
import com.baemin.vo.Store;

public interface StoreDAO {

	List<Store> storeList(int category, int address1);

	Store storeDetail(int id);

	List<Food> foodList(int id);

	List<String> foodOption(int foodId);


}
