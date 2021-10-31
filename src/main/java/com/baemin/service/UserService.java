package com.baemin.service;

import java.util.List;

import com.baemin.vo.Point;
import com.baemin.vo.Review;
import com.baemin.vo.User;

public interface UserService {
	
	// 닉네임,이메일,유저네임 중복체크
	public int overlapCheck(String value, String valueType);

	public void join(User user);

	public List<Review> myReviewList(long id);

	public void deleteReview(String orderNum);

	public List<Point> myPoint(long id);

	public void infoModify(String value, String valueType, long id);

	public List<String> findId(String email);
	

}
