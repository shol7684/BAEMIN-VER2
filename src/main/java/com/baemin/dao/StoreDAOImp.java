package com.baemin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baemin.vo.Food;
import com.baemin.vo.FoodOption;
import com.baemin.vo.Review;
import com.baemin.vo.Store;

@Repository
public class StoreDAOImp implements StoreDAO {

	@Autowired
	private SqlSession sql;

	@Override
	public List<Store> storeList(Map map) {

		return sql.selectList("store.storeList", map);
	}

	@Override
	public Store storeDetail(long id) {
		return sql.selectOne("store.storeDetail", id);
	}

	@Override
	public List<Food> foodList(long id) {
		return sql.selectList("store.foodList", id);
	}

	@Override
	public List<FoodOption> foodOption(int foodId) {
		return sql.selectList("store.foodOption", foodId);
	}

	@Override
	public void reviewWrite(Review review) {
		sql.insert("store.reviewWrite", review);
	}

	@Override
	public void reviewModify(Review review) {
		sql.update("store.reviewModify", review);
	}

	@Override
	public List<Review> reviewList(long id) {
		return sql.selectList("store.reviewList", id);
	}

	@Override
	public void addLikes(Map<String, Long> map) {
		sql.insert("store.addLikes", map);
	}

	@Override
	public void deleteLikes(Map<String, Long> map) {
		sql.insert("store.deleteLikes", map);
	}

	@Override
	public int isLikes(Map<String, Long> m) {
		return sql.selectOne("store.isLikes", m);
	}

	@Override
	public List<Store> likesList(long userId) {
		return sql.selectList("store.likesList", userId);
	}
	
	@Override
	public List<Store> likesListNonUser(String likes) {
		return sql.selectList("store.likesListNonUser", likes);
	}
	

	@Override
	public List<Store> storeSearch(int address1, String keyword, int pageStart, int pageEnd) {
		Map<String, Object> map = new HashMap<>();
		map.put("address1", address1);
		map.put("keyword", keyword);
		map.put("pageStart", pageStart);
		map.put("pageEnd", pageEnd);

		return sql.selectList("store.storeSearch", map);
	}



}
