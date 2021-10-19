package com.baemin.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baemin.vo.User;
import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;

@Repository
public class UserDAOImp implements UserDAO {
	
	@Autowired
	private SqlSession sql;

	@Override
	public int overlapCheck(String value, String valueType) {
		
		Map<String, String> map = new HashMap<>();
		map.put("value", value);
		map.put("valueType", valueType);
		
		return sql.selectOne("user.overlapCheck" ,map);
	}

	@Override
	public void join(User user) {
		sql.insert("user.join" , user);
	}
	

}
