package com.baemin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baemin.dao.UserDAO;
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
	

}
