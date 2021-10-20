package com.baemin.dao;

import java.util.List;
import java.util.Map;

import com.baemin.vo.Cart;
import com.baemin.vo.OrderInfo;
import com.baemin.vo.OrderList;

public interface OrderDAO {
	// 메뉴 총합가격 계산시 배달팁 가져오기
	int getDeleveryTip(int storeId);
	
	//	메뉴 총합가격 계산시 음식가격
	List<Long> foodPriceList(List<Cart> cartList);
	
	//	메뉴 총합가격 계산시 음식 추가 옵션가격
	List<Integer> optionPriceList(List<Cart> cartList);

	// 주문 정보 입력
	void order(OrderInfo info);
	
	// 주문 상세정보 입력
	void orderDetail(Map<String, String> orderDetail);
	
	// 주문목록 페이지
	List<OrderList> orderList(long userId);
	
	// 주문목록 상세보기 페이지
	OrderList orderListDetail(String orderNum);




}
