package com.baemin.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.baemin.util.Page;
import com.baemin.vo.Food;
import com.baemin.vo.OrderList;
import com.baemin.vo.Sales;
import com.baemin.vo.Store;

public interface AdminService {

	// 관리자 메인 주문 목록
	List<OrderList> orderList(String list, Optional<Integer> page);

	// 매장관리 탭 가게 목록
	List<Store> storeList(Page p);

	List<Store> storeList(Page p, String keyword);

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
	Map<String, Object> pointRegist(String giftCardNum, long id);

	int orderCancle(OrderList orderList, String cancleReason) throws IOException, ParseException ;
	
	int orderComplete(String orderNum, long userId);

	List<Sales> sales(String time, String month);

	void modifyMenu(Food food, String[] foodOption, Integer[] foodOptionPrice, Integer[] optionId);

	void deleteOption(long optionId);

	

	

	

	

}
