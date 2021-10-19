package com.baemin.service;

import java.util.Map;

import com.baemin.config.LoginDetail;

public interface OrderService {
	//	장바구니에 담긴 금액과 db의 금액이 같은지 확인
	long orderPriceCheck(Map cartMap);

	void payment(Map cartMap, LoginDetail user);

}
