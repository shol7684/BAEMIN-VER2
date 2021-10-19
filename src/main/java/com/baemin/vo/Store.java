package com.baemin.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Store {
	
	private long id;
	private int category;
	private String storeName;
	private int storeAddress1;
	private String storeAddress2;
	private String storeAddress3;
	private String storePhone;
	private String storeImg;
	private String storeThumb;
	private int openingTime;
	private int closingTime;
	private long minDelevery;
	private int deleveryTime;
	private double score;
	private long deleveryTip;
	private String storeDes;
	
	
	
}
