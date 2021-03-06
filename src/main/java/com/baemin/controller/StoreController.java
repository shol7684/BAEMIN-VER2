package com.baemin.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.baemin.util.CookieManager;
import com.baemin.util.Page;
import com.baemin.util.UploadFile;
import com.baemin.vo.FoodOption;
import com.baemin.vo.Review;
import com.baemin.vo.Store;
import com.baemin.vo.StoreDetail;

import edu.emory.mathcs.backport.java.util.Arrays;

@Controller
public class StoreController {

	@Autowired
	private StoreService storeService;

	@Autowired
	private UploadFile uploadFile;
	
	@Autowired
	private CookieManager cookieManager;
	
	private static final Logger LOGGER = LogManager.getLogger(StoreController.class);


	@GetMapping("/store/{category}/{address1}")
	public String store(@PathVariable int category, @PathVariable int address1, Model model) {

		LOGGER.info("가게 카테고리 : " + category + " 우편번호 : " + address1);
		List<Store> storeList = storeService.storeList(category, address1 / 100);
		model.addAttribute("storeList", storeList);
		return "store/store";
	}

	@ResponseBody
	@GetMapping("/store/storeList")
	public ResponseEntity<List<Store>> sortStore(int category, int address1, String sort, int page,  Model model) {
		List<Store> storeList = storeService.storeList(category, address1 / 100, sort, page);
		return new ResponseEntity<>(storeList, HttpStatus.OK);
	}
	

	@GetMapping("/store/detail/{id}")
	public String storeDetail(@PathVariable long id, Model model, @AuthenticationPrincipal LoginDetail user, HttpServletRequest request) throws NotFoundException, UnsupportedEncodingException {
		long userId = 0;
		String role = "";
		if (user != null) {
			userId = user.getUser().getId();
			role = user.getUser().getRole();
		} else {
			String likesList = cookieManager.findCookie("likesList", request);
			if(likesList == null ) {
				model.addAttribute("isLikes", false);
			} else {
				String[] arr = likesList.split(",");
				boolean isLikes = Arrays.asList(arr).contains(id+"");
				model.addAttribute("isLikes", isLikes);
			}
		}

		StoreDetail storeDetail = storeService.storeDetail(id, userId);

		model.addAttribute("store", storeDetail);
		model.addAttribute("role", role);

		return "store/detail";
	}
	
	
	

	// 메뉴 클릭시 음식 추가옵션 가져요기
	@ResponseBody
	@GetMapping("/foodOption")
	public List<FoodOption> menuDetail(int foodId) {
		List<FoodOption> foodOption = storeService.foodOption(foodId);
		return foodOption;
	}

	// 리뷰 작성
	@PostMapping("/store/review")
	public String review(Review review, MultipartFile file, @AuthenticationPrincipal LoginDetail user) throws IOException {

		if (file.isEmpty()) {
			String img = "";
			review.setReviewImg(img);
		} else {
			String img = uploadFile.fildUpload(file);
			review.setReviewImg(img);
		}
		long userId = user.getUser().getId();
		review.setUserId(userId);

		storeService.reviewWrite(review);

		return "redirect:/orderList";
	}

	// 리뷰 수정
	@PostMapping("/store/reviewModify")
	public String reviewModify(Review review, MultipartFile file, @AuthenticationPrincipal LoginDetail user) throws IOException {

		if(!file.isEmpty()){
			String img = uploadFile.fildUpload(file);
			review.setReviewImg(img);
		}
		long userId = user.getUser().getId();
		review.setUserId(userId);

		System.out.println(review);
		storeService.reviewModify(review);

		return "redirect:/orderList";
	}

	// 찜하기
	@ResponseBody
	@PostMapping("/store/likes")
	public long likes(long id, String likes, @AuthenticationPrincipal LoginDetail user, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		System.out.println("찜하기id =  " + id + " " + likes);
		long userId = 0;
		if (user == null) {
			System.out.println("찜하기 비회원");
			String likesList = cookieManager.findCookie("likesList", request);
			LinkedHashSet<String> list = cookieManager.add(likesList, id+"");
			cookieManager.addCookie(list.toString(), "likesList", response);
			
		} else {
			System.out.println("찜하기 회원");
			userId = user.getUser().getId();
			storeService.likes(id, likes, userId);
		}

		return userId;

	}

	// 찜한 가게 목록
	@GetMapping("/likes/store")
	public String likes(Model model, @AuthenticationPrincipal LoginDetail user, HttpServletRequest request) throws UnsupportedEncodingException {

		long userId = 0;

		List<Store> likesList = new ArrayList<>();
		if (user == null) {
			String likes = cookieManager.findCookie("likesList", request);
			
			if(likes != null && !likes.equals("")) {
				likesList = storeService.likesListNonUser(likes);
			}
			
		} else {
			userId = user.getUser().getId();
			likesList = storeService.likesList(userId);
		}
		model.addAttribute("likesList", likesList);

		return "/store/likes";
	}

	@GetMapping({"/store/search", "/store/search/{movePage}"})
	public String search(String address1, String keyword, Model model, @PathVariable Optional<Integer> movePage, HttpServletResponse response,
			HttpServletRequest request) throws UnsupportedEncodingException {

		CookieManager cookieManager = new CookieManager();
		
		String findCookie = cookieManager.findCookie("keyword", request);
		
		LinkedHashSet<String> set = null;
		
		if(keyword == null ) {
			set = cookieManager.cookieSave(response, findCookie);
			
		} else {
			set = cookieManager.cookieSave(response, keyword, findCookie);
			cookieManager.addCookie(set.toString(), "keyword", response);
		}
		
		// 검색어 쿠키 목록
		model.addAttribute("searchKeyword", set);
	
		// 방금 검색한 키워드
		model.addAttribute("keyword", keyword);
		
		if (address1 != null && !address1.equals("")) {
			Page p = new Page(movePage);
			
			List<Store> store = storeService.storeSearch(Integer.parseInt(address1) / 100, keyword, movePage);
			
			if(store.size() == 0 ) {
				model.addAttribute("nosuch", true);
			}
			else {
				model.addAttribute("address1", address1);
				model.addAttribute("store", store);
				model.addAttribute("lastPage", p.lastPage(store.get(0).getListCount()));
				model.addAttribute("page", new Page(movePage));
			}
		}
		return "store/search";
	}

	@ResponseBody
	@DeleteMapping("/store/keyword-all")
	public void keywordDelete(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
		Cookie cookie = new Cookie("keyword", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	@ResponseBody
	@DeleteMapping("/store/keyword-one")
	public void keywordDelete(String keyword, HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		CookieManager cookieManager = new CookieManager();
		String findCookie = cookieManager.findCookie("keyword", request);
		cookieManager.removeKeyword(response, findCookie, keyword);
	}

}
