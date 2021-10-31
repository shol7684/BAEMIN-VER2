package com.baemin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.baemin.config.LoginDetail;
import com.baemin.vo.OrderInfo;
import com.baemin.vo.OrderList;

public interface OrderService {
	//	장바구니에 담긴 금액과 db의 금액이 같은지 확인
	long orderPriceCheck(Map cartMap);
	
	// 주문하기
	String order(Map cartMap, OrderInfo info, LoginDetail user, HttpSession session);

	// 주문목록
	List<OrderList> orderList(long userId);

	// 주문목록 상세보기
	OrderList orderListDetail(String orderNum);


}
