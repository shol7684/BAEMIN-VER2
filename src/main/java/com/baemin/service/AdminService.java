package com.baemin.service;

import java.util.List;
import java.util.Map;

import com.baemin.util.Page;
import com.baemin.vo.Food;
import com.baemin.vo.OrderList;
import com.baemin.vo.Store;

public interface AdminService {

	// 관리자 메인 주문 목록
	List<OrderList> orderList(String list);

	// 매장관리 탭 가게 목록
	List<Store> storeList(Page p);

	// 매장관리 탭 가게 등록하기
	void storeRegist(Store store);

	// 리뷰 답장달기
	String bossComment(String orderNum, String bossComment);

	void menuDelete(int storeId, long[] deleteNumber);

	void addMenu(Food food, String[] foodOption, Integer[] foodOptionPrice);

	void storeModify(Store store);

	// 주문목록에서 주문수락하기
	int orderAccept(String orderNum, int time, long userId);

	// 상품권 등록
	int pointRegist(String giftCardNum, long id);

	// 사용자가 주문시 실시간 주문요청 받기 웹소켓
	OrderList getOrderOne(String orderNum);

	int orderCancle(String orderNum, String cancleReason, long userId);

}
