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
	private long storeId;
	private long userId;
	private Timestamp orderDate;
	private String deleveryStatus;
	private int deleveryAddress1;
	private String deleveryAddress2;
	private String deleveryAddress3;
	private String payMethod;
	private int totalPrice;
	private int usedPoint;
	private String phone;
	private String request;

	private String foodInfo;
	
	
	private String storeName;
	private String storeImg;
	private String StoreThumb;
	private int deleveryTip;
	
	// 리뷰 상태 추가
	private String reviewContent;
	private float score;
	private String reviewImg;
	
	
	// 관리자 페이지 목록 수 추가
	private int count1;	// 주문접수 대기 중
	private int count2; // 처리 중
	
	// 주문목록 총 갯수
	private int listCount;
	
	// 아임포트 결제번호 추가
	private String impUid; 

}
