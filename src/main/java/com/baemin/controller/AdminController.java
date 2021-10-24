package com.baemin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.baemin.service.AdminService;
import com.baemin.service.StoreService;
import com.baemin.util.FoodInfoFromJson;
import com.baemin.vo.Cart;
import com.baemin.vo.Food;
import com.baemin.vo.OrderList;
import com.baemin.vo.Store;
import com.google.gson.Gson;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private StoreService storeService;
	
	private static final Logger LOGGER = LogManager.getLogger(AdminController.class);

	@GetMapping("/admin/main")
	public String adminHome() {

		return "admin/main";
	}

	@ResponseBody
	@GetMapping("/admin/orderList")
	public List<Map> orderList(String list) {

		System.out.println(list);
		
		List<OrderList> orderList = adminService.orderList(list);
		
		List<Map> deleveryInfo = new ArrayList<>();
		
		for(int i=0;i<orderList.size();i++) {
			deleveryInfo.add(FoodInfoFromJson.foodInfoFromJson(orderList.get(i)));
		}
		
		return deleveryInfo;
	}

	@GetMapping("/admin/storeManagement")
	public String storeManagement(Model model) {

		List<Store> storeList = adminService.storeList();

		model.addAttribute("storeList", storeList);

		return "admin/storeManagement";
	}
	
	
	@PostMapping("/admin/storeRegist")
	public String storeRegist(Store store, MultipartFile file) {
		
		System.out.println(store);
		
		if(file.isEmpty()) {
			String img = File.separator + "img" + File.separator + "none.gif";
			store.setStoreImg(img);
			store.setStoreThumb(img);
		} else {
			
		}
		
		for(int i=0;i<30;i++) {
			String s = store.getStoreName() + i;
			store.setStoreName(s);
			
			adminService.storeRegist(store);
		}
		
		
		
		return "redirect:/admin/storeManagement";
	}
	
	
	@GetMapping("/admin/detail/{id}")
	public String adminStoreDetail(@PathVariable int id,Model model, @AuthenticationPrincipal LoginDetail user) {
		
		long userId = 0;
		String role = "";
		if(user != null) {
			userId = user.getUser().getId();
			role = user.getUser().getRole();
			model.addAttribute("adminPage" , true);
		}
		
		Map<String, Object> store = storeService.storeDetail(id, userId);

		System.out.println("가게 정보 = " + store);

		model.addAttribute("store", store);
		model.addAttribute("role", role);
		
		return "admin/adminStoreDetail";
	}
	
	@ResponseBody
	@PostMapping("/admin/bossComment")
	public String bossComment(String orderNum, String bossComment, String clickBtn) {
		bossComment = adminService.bossComment(orderNum, bossComment);
		
		return bossComment;
	}
	
	
	
	@ResponseBody
	@DeleteMapping("/admin/menu")
	public void deleteMenu(int storeId, long[] deleteNumber) {
		
		System.out.println("storeId = " + storeId);
		System.out.println("deleteNumber = " + Arrays.toString(deleteNumber));
		
		adminService.menuDelete(storeId, deleteNumber);
		
	}
	
	@PostMapping("/admin/menu")
	public String addMenu(Food food, String[] foodOption, Integer[] foodOptionPrice, MultipartFile file) {
		
		System.out.println("food = " + food);
		System.out.println("foodOption= " + Arrays.toString(foodOption));
		System.out.println("foodOptionPrice= " + Arrays.toString(foodOptionPrice));
//		이미지 첨부 x 
		if(file.isEmpty()) {
			String img = File.separator + "img" + File.separator + "none.gif";
			food.setFoodImg(img);
			food.setFoodThumb(img);
			
		} 
//		이미지 첨부 o 
		else {
		}
		
		adminService.addMenu(food, foodOption, foodOptionPrice);
		
		
		
		
		
		
		
		return "redirect:/admin/detail/" + food.getStoreId() ;
	}
	
	
	
	@PostMapping("/admin/storeModify")
	public String storeModify(Store store) {
		adminService.storeModify(store);
		return "redirect:/admin/detail/" + store.getId();
	}
	
	
	@ResponseBody
	@PostMapping("/admin/orderAccept")
	public String orderAccept(String orderNum, int time, long userId) {
		System.out.println(orderNum);
		System.out.println(time);
		System.out.println(userId);
		
		adminService.orderAccept(orderNum, time, userId);
		
		return "";
	}
	
	
	

}
 