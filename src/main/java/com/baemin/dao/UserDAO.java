package com.baemin.dao;

import java.util.List;

import com.baemin.vo.Point;
import com.baemin.vo.Review;
import com.baemin.vo.User;

public interface UserDAO {

	public int overlapCheck(String value, String valueType);

	public void join(User user);

	public List<Review> myReviewList(long id);

	public void deleteReview(String orderNum);

	public List<Point> myPoint(long id);

	public void modifyInfo(String valueType, String value, long id);

	public List<String> findId(String email);

	public String authCheck(String username, String value, String valueType);
	
	
}
