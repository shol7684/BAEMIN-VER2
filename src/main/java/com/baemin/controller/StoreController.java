package com.baemin.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.mail.Multipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		
		if(user == null) {
			
		} else {
			userId = user.getUser().getId();
			
			List likesList = storeService.likesList(userId);
			model.addAttribute("likesList" , likesList);
		}
		
		
		return "/store/likes";
	}

}
