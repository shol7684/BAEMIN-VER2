package com.baemin.service;

import java.util.List;
import java.util.Map;

import com.baemin.vo.Store;

public interface StoreService {

	List<Store> storeList(int category, int address);

	Map storeDetail(int id);

	List<String> foodOption(int foodId);

	
}
