package com.baemin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baemin.vo.Point;
import com.baemin.vo.Review;
import com.baemin.vo.User;

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

	@Override
	public List<Review> myReviewList(long id) {
		return sql.selectList("user.myReviewList", id);
	}

	@Override
	public void deleteReview(String orderNum) {
		sql.delete("user.deleteReview" , orderNum);
	}

	@Override
	public List<Point> myPoint(long id) {
		return sql.selectList("user.myPoint", id);
		
	}

	@Override
	public void modifyInfo(String valueType, String value, long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("value", value);
		map.put("valueType", valueType);
		map.put("id", id);
		sql.update("user.infoModify" , map);
	}

	@Override
	public List<String> findId(String email) {
		
		System.out.println("userDAO email = "  + email);
		
		return sql.selectList("user.findId", email);
	}

	@Override
	public String authCheck(String username, String value, String valueType) {
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		map.put("value", value);
		map.put("valueType", valueType);
		return sql.selectOne("user.authCheck", map);
	}
	

}
