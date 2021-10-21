package com.baemin.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Review {
	private String orderNum; 
	private long storeId;
	private String reviewContent;
	private String bossComment;
	private Date regiDate;
	private float score;
	private String reviewImg;
	
	private long userId;
	private String username;
	private String nickname;
	
	private String storeName; // 내 리뷰목록 추가
}