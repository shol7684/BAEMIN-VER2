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

	@GetMapping("store/{category}/{address1}")
	public String store(@PathVariable int category, @PathVariable int address1, Model model) {

		System.out.println(category);
		System.out.println(address1);

		List<Store> storeList = storeService.storeList(category, address1 / 100);

		model.addAttribute("storeList", storeList);

		return "store/store";
	}

	@GetMapping("/store/detail/{id}")
	public String storeDetail(@PathVariable int id, Model model) {

		Map<String, Object> store = storeService.storeDetail(id);

		System.out.println("가게 정보 = " + store);

		model.addAttribute("store", store);

		return "store/detail";
	}

	@ResponseBody
	@GetMapping("/foodOption")
	public List menuDetail(int foodId) {
		List<String> foodOption = storeService.foodOption(foodId);

		return foodOption;
	}

	@PostMapping("/store/review")
	public String review(Review review, MultipartFile file, @AuthenticationPrincipal LoginDetail user) {

		System.out.println(file);
		System.out.println(file.getOriginalFilename());

		System.out.println(file.isEmpty());

		// 이미지 첨부 x
		if (file.isEmpty()) {
			String img = File.separator + "img" + File.separator + "none.gif";
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

}
