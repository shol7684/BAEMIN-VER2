package com.baemin.vo;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderList {

	private String orderNum;
	private long userId;
	private String phone;
	private Timestamp orderDate;
	private String payMethod;
	private String deleveryStatus;
	private int deleveryAddress1;
	private String deleveryAddress2;
	private String deleveryAddress3;
	private long storeId;
	private String amount;
	private String foodPrice;
	private long totalPrice;
	private String foodInfo;
	private String storeName;
	private String storeImg;
	private String StoreThumb;
	private long deleveryTip;
	private int usedPoint;
	private String request;
	
	// 리뷰 상태 추가
	private String reviewContent;
	private float score;
	private String reviewImg;
	
	
	// 관리자 페이지 목록 수 추가
	private int count1;	// 주문접수 대기 중
	private int count2; // 처리 중
	private int count3; // 완료
	
	// 주문목록 총 갯수
	private int listCount;
}
