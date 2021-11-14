package com.baemin.util;

import java.util.Optional;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Page {

	private int view; // 한 화면에 보여줄 갯수
	private int pageCount = 10; // 페이지 갯수
	
	private int pageStart; // 페이지 첫번째 목록
	private int pageEnd; // 페이지 마지막 목록
	
	private int pageNum; // 현재화면 페이지 첫번째 6~10 => 6, 11~15 => 11 
	private int nextPage; 
	private int prevPage;
	
	private int nowPage;
	
	
	public Page(Optional<Integer> movePage) {
		this(movePage, 10);
	}
	
	public Page(Optional<Integer> movePage, int view) {
		int page = 1;
		this.view = view;
		
		if(movePage.isPresent()) {
			 page = (int) movePage.get();
		}
		
		this.pageStart = (page * view) - view + 1;
		this.pageEnd = page * view;
		
		this.pageNum = page - (page -1) % pageCount;
		this.nextPage = this.pageNum + pageCount;
		this.prevPage = page - (page- pageNum) -1;
		
		this.nowPage = page;
	}
	
	
	
	public Page(int movePage) {
		this(movePage, 10);
	}
	
	public Page(int movePage, int view) {
		int page = movePage;
		this.view = view;
		
		this.pageStart = (page * view) - view + 1;
		this.pageEnd = page * view;
		
		this.pageNum = page - (page -1) % pageCount;
		this.nextPage = this.pageNum + pageCount;
		this.prevPage = page - (page- pageNum) -1;
		
		this.nowPage = page;
	}
	
	
	
	
	
	public int lastPage(int listCount) {
		if(listCount % view == 0) {
			return listCount / view ;
		}
		return listCount / view +1;
	}
	
	
}
