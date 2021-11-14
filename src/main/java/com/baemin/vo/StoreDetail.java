package com.baemin.vo;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class StoreDetail {
	@Setter @Getter
	private Store storeInfo;
	@Setter @Getter
	private List<Food> foodList;
	@Setter @Getter
	private List<Review> reviewList;
	
	// 회원용 찜 
	private boolean isLikes;

	public StoreDetail() {}
	
	public StoreDetail(Store storeInfo, List<Food> foodList, List<Review> reviewList, boolean isLikes) {
		this.storeInfo = storeInfo;
		this.foodList = foodList;
		this.reviewList = reviewList;
		this.isLikes = isLikes;
	}

	public boolean getIsLikes() {
		return isLikes;
	}

	
	

}
