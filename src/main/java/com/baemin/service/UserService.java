package com.baemin.service;

import com.baemin.vo.User;

public interface UserService {
	
	// 닉네임,이메일,유저네임 중복체크
	public int overlapCheck(String value, String valueType);

	public void join(User user);
	

}
