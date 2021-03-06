package com.baemin.dao;

import java.util.List;
import java.util.Map;

import com.baemin.vo.Cart;
import com.baemin.vo.OrderDetail;
import com.baemin.vo.OrderInfo;
import com.baemin.vo.OrderList;

public interface OrderDAO {
	// 메뉴 총합가격 계산시 배달팁 가져오기
	int getDeleveryTip(long storeId);
	
	//	메뉴 총합가격 계산시 음식가격
	List<Integer> foodPriceList(List<Cart> cartList);
	
	//	메뉴 총합가격 계산시 음식 추가 옵션가격
	List<Integer> optionPriceList(List<Cart> cart);

	// 주문 정보 입력
	void order(OrderInfo info);
	
	// 주문 상세정보 입력
//	void orderDetail(Map<String, String> orderDetail);
	void orderDetail(OrderDetail[] detail, long userId);
	
	// 주문목록 페이지
	List<OrderList> orderList(long userId, int startPage, int endPage);
	
	// 주문목록 상세보기 페이지
	OrderList orderListDetail(String orderNum);

	




}
