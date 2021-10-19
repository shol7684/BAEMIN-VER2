package com.baemin.dao;

import com.baemin.vo.User;

public interface UserDAO {

	public int overlapCheck(String value, String valueType);

	public void join(User user);
	
	
}
