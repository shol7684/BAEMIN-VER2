package com.baemin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baemin.dao.UserDAO;
import com.baemin.vo.Point;
import com.baemin.vo.Review;
import com.baemin.vo.User;

@Service
public class UserServiceImp implements UserService {

	@Autowired 
	private UserDAO userDAO;
	
	@Override
	public int overlapCheck(String value, String valueType) {
		return userDAO.overlapCheck(value, valueType);
	}

	@Override
	public void join(User user) {
		userDAO.join(user);
	}

	@Override
	public List<Review> myReviewList(long id) {
		return userDAO.myReviewList(id);
	}

	@Override
	public void deleteReview(String orderNum) {
		userDAO.deleteReview(orderNum);
		
	}

	@Override
	public List<Point> myPoint(long id) {
		return userDAO.myPoint(id);
		
	}
	

}
