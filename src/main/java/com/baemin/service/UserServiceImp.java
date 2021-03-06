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

//	@Override
//	public void infoModify(String value, String valueType, long id) {
//		userDAO.infoModify(value, valueType, id);
//	}

	@Override
	public void modifyPassword(long userId, String password) {
		userDAO.modifyInfo("password", password, userId);
	}

	@Override
	public void modifyNickname(long userId, String nickname) {
		 userDAO.modifyInfo("nickname", nickname, userId);
		
	}

	@Override
	public void modifyPhone(long userId, String phone) {
		 userDAO.modifyInfo("phone", phone, userId);
		
	}

	@Override
	public List<String> findId(String email) {
		return userDAO.findId(email);
	}

	@Override
	public String authCheck(String username, String value, String valueType) {
		return userDAO.authCheck(username, value, valueType);
	}

	

}
