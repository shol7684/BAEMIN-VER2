package com.baemin.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.javassist.NotFoundException;

import com.baemin.vo.FoodOption;
import com.baemin.vo.Review;
import com.baemin.vo.Store;
import com.baemin.vo.StoreDetail;

public interface StoreService {
	// 가게 기본정렬
	List<Store> storeList(int category, int address, int page);
	
	// 가게 정렬버튼
	List<Store> storeList(int category, int i, String sort, int j);

	// 가게 상세
	StoreDetail storeDetail(long storeId, long userId) throws NotFoundException;

	// 해당 메뉴의 옵션 가져오기
	List<FoodOption> foodOption(int foodId);

	void reviewWrite(Review review);

	void reviewModify(Review review);

	// 찜
	void likes(long storeId, String likes, long userId);

	// 내가 찜한 가게들
	List likesList(long userId);

	List<Store> storeSearch(int address1, String keyword, Optional<Integer> movePage);

	

	
}
