package com.baemin.vo;

import java.sql.Date;

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
	private Date orderDate;
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
	
}
