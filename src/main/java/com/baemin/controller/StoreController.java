package com.baemin.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baemin.config.LoginDetail;
import com.baemin.service.StoreService;
import com.baemin.vo.Review;
import com.baemin.vo.Store;

@Controller
public class StoreController {

	@Autowired
	private StoreService storeService;

	private static final Logger LOGGER = LogManager.getLogger(StoreController.class);

	@GetMapping("/store/{category}/{address1}")
	public String store(@PathVariable int category, @PathVariable int address1, Model model) {

		System.out.println(category);
		System.out.println(address1);
		int page = 1;

		List<Store> storeList = storeService.storeList(category, address1 / 100, page);

		model.addAttribute("storeList", storeList);

		return "store/store";
	}

	@ResponseBody
	@GetMapping("/storeNextPage")
	public List<Store> storeNextPage(int category, int address1, int page) {

		List<Store> storeList = storeService.storeList(category, address1 / 100, page);

		return storeList;
	}

	@GetMapping("/store/detail/{id}")
	public String storeDetail(@PathVariable long id, Model model, @AuthenticationPrincipal LoginDetail user) {

		long userId = 0;
		String role = "";
		if (user != null) {
			userId = user.getUser().getId();
			role = user.getUser().getRole();
		}

		Map<String, Object> store = storeService.storeDetail(id, userId);

		System.out.println("가게 정보 = " + store);

		model.addAttribute("store", store);
		model.addAttribute("role", role);

		return "store/detail";
	}

	// 메뉴 클릭시 음식 추가옵션 가져요기
	@ResponseBody
	@GetMapping("/foodOption")
	public List menuDetail(int foodId) {
		List<String> foodOption = storeService.foodOption(foodId);

		return foodOption;
	}

	// 리뷰 작성
	@PostMapping("/store/review")
	public String review(Review review, MultipartFile file, @AuthenticationPrincipal LoginDetail user) {

		System.out.println(file);
		System.out.println(file.getOriginalFilename());

		System.out.println(file.isEmpty());

		// 이미지 첨부 x
		if (file.isEmpty()) {
			String img = "";
			review.setReviewImg(img);
		}

		// 이미지 첨부 o
		else {
			String img = File.separator + "upload" + File.separator + file.getOriginalFilename();
			review.setReviewImg(img);
		}
		long userId = user.getUser().getId();
		review.setUserId(userId);

		System.out.println("유져" + review);

		storeService.reviewWrite(review);

		return "redirect:/orderList";
	}

	// 리뷰 수정
	@PostMapping("/store/reviewModify")
	public String reviewModify(Review review, MultipartFile file, @AuthenticationPrincipal LoginDetail user) {

		// 이미지 첨부 x
		if (file.isEmpty()) {
			String img = "";
			review.setReviewImg(img);
		}
		// 이미지 첨부 o
		else {
			String img = File.separator + "upload" + File.separator + file.getOriginalFilename();
			review.setReviewImg(img);
		}
		long userId = user.getUser().getId();
		review.setUserId(userId);

		storeService.reviewModify(review);

		return "redirect:/orderList";
	}

	// 찜하기
	@ResponseBody
	@PostMapping("/store/likes")
	public long likes(long id, String likes, @AuthenticationPrincipal LoginDetail user) {

		System.out.println("찜하기id =  " + id);
		System.out.println("찜하기id =  " + likes);
		long userId = 0;
		if (user == null) {
			System.out.println("찜하기 비회원");
		} else {
			System.out.println("찜하기 회원");
			userId = user.getUser().getId();
			storeService.likes(id, likes, userId);
		}

		return userId;

	}

	// 찜한 가게 목록
	@GetMapping("/likes/store")
	public String likes(Model model, @AuthenticationPrincipal LoginDetail user) {

		long userId = 0;

		if (user == null) {

		} else {
			userId = user.getUser().getId();

			List likesList = storeService.likesList(userId);
			model.addAttribute("likesList", likesList);
		}

		return "/store/likes";
	}

	@GetMapping("/store/search")
	public String search(String address1, String keyword, Model model, HttpServletResponse response,
			HttpServletRequest request) throws UnsupportedEncodingException {

		System.out.println("keyword = " + keyword);
		System.out.println("address1 = " + address1);

		Cookie[] cookies = request.getCookies();

		String cookieKeyword = "";

		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("keyword")) {
				cookieKeyword = URLDecoder.decode(cookies[i].getValue(), "UTF-8");
				break;
			}
		}

		LinkedHashSet<String> set = new LinkedHashSet<>();
		StringTokenizer st = new StringTokenizer(cookieKeyword, ", ");

		if(keyword != null ) {
			set.add(keyword);
		}
		
		while (st.hasMoreTokens()) {
			set.add(st.nextToken());
			
			if(set.size() > 7) {
				break;
			}
		}

		model.addAttribute("searchKeyword", set);
			
			
		// 쿠키 저장
		String s = set.toString();
		s = s.substring(1, s.length() - 1); // 괄호 제거

		Cookie cookie = new Cookie("keyword", URLEncoder.encode(s, "UTF-8"));
		cookie.setMaxAge(60*60*24*30);
		response.addCookie(cookie);


		if (address1 != null && !address1.equals("")) {

			List<Store> store = storeService.storeSearch(Integer.parseInt(address1) / 100, keyword);
			model.addAttribute("store", store);

			System.out.println(store);

		}
		
		model.addAttribute("keyword", keyword);

		return "store/search";
	}
	
	@ResponseBody
	@DeleteMapping("/store/keyword-all")
	public void keywordDelete(HttpServletResponse response) {
		Cookie cookie = new Cookie("keyword", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	
	
	@ResponseBody
	@DeleteMapping("/store/keyword-one")
	public void keywordDelete(String keyword, HttpServletResponse response, HttpServletRequest request ) throws UnsupportedEncodingException {
		
		Cookie[] cookies = request.getCookies();

		String cookieKeyword = "";

		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("keyword")) {
				cookieKeyword = URLDecoder.decode(cookies[i].getValue(), "UTF-8");
				break;
			}
		}

		LinkedHashSet<String> set = new LinkedHashSet<>();
		StringTokenizer st = new StringTokenizer(cookieKeyword, ", ");

		
		while (st.hasMoreTokens()) {
			set.add(st.nextToken());
			
			if(set.size() > 7) {
				break;
			}
		}

		set.remove(keyword);
		
		String s = set.toString();
		s = s.substring(1, s.length() - 1); // 괄호 제거

		Cookie cookie = new Cookie("keyword", URLEncoder.encode(s, "UTF-8"));
		cookie.setMaxAge(60*60*24*30);
		response.addCookie(cookie);
		
		
	}
	
	

}
