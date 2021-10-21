package com.baemin.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baemin.service.AdminService;
import com.baemin.service.StoreService;
import com.baemin.vo.OrderList;
import com.baemin.vo.Store;

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
	public List<OrderList> orderList() {

		List<OrderList> orderList = adminService.orderList();
		System.out.println("관리자 메인 주문 목록 = " + orderList);

		return orderList;
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
		
		adminService.storeRegist(store);
		
		
		return "redirect:/admin/storeManagement";
	}
	
	
	@GetMapping("/admin/detail/{id}")
	public String adminStoreDetail(@PathVariable int id,Model model) {
		
		Map<String, Object> store = storeService.storeDetail(id);

		System.out.println("가게 정보 = " + store);

		model.addAttribute("store", store);
		
		return "store/detail";
	}
	
	@ResponseBody
	@PostMapping("/admin/bossComment")
	public String bossComment(String orderNum, String bossComment) {
		
		System.out.println("bossComment = " + bossComment);
		
//		adminService.bossComment(orderNum, bossComment);
		
		
		return bossComment;
	}
	
	
	
	
	

}
