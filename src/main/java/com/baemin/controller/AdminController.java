package com.baemin.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baemin.config.LoginDetail;
import com.baemin.service.AdminService;
import com.baemin.service.StoreService;
import com.baemin.util.FoodInfoFromJson;
import com.baemin.util.Page;
import com.baemin.util.UploadFile;
import com.baemin.util.UserInfoSessionUpdate;
import com.baemin.vo.Cart;
import com.baemin.vo.Food;
import com.baemin.vo.OrderList;
import com.baemin.vo.Sales;
import com.baemin.vo.Store;
import com.baemin.vo.StoreDetail;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private UploadFile uploadFile;
	
	private static final Logger LOGGER = LogManager.getLogger(AdminController.class);

	
	@GetMapping("/admin/main")
	public String adminHome() {

		return "admin/main";
	}

	@ResponseBody
	@GetMapping("/admin/orderList")
	public Map<String, Object> orderList(String list, Optional<Integer> page) {
		List<OrderList> orderList = adminService.orderList(list, page);
		List<List<Cart>> cartList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		
		for (int i=0;i<orderList.size();i++) {
			cartList.add(FoodInfoFromJson.foodInfoFromJson(orderList.get(i).getFoodInfo()));
		}
		
		map.put("orderList", orderList);
		map.put("cartList", cartList);
		
		return map;
	}
	
	

	@GetMapping({"/admin/storeManagement", "/admin/storeManagement/{movePage}"})
	public String storeManagement(Model model, @PathVariable Optional<Integer> movePage, String keyword) {

		Page p = new Page(movePage, 20);
		
		List<Store> storeList = null;
		if(keyword == null ) {
			 storeList = adminService.storeList(p);
		} else {
			storeList = adminService.storeList(p,keyword);
		}
		
		if(storeList.size() == 0) {
			model.addAttribute("nosuch", true);
			return "admin/storeManagement";
		}
		
		model.addAttribute("lastPage", p.lastPage(storeList.get(0).getListCount()));
		model.addAttribute("page", p);
		model.addAttribute("storeList", storeList);
		
		System.out.println(storeList);

		return "admin/storeManagement";
	}
	
	
	@PostMapping("/admin/storeRegist")
	public String storeRegist(Store store, MultipartFile file) throws IOException {
		
		if(file.isEmpty()) {
			String img = File.separator + "img" + File.separator + "none.gif";
			store.setStoreImg(img);
			store.setStoreThumb(img);
		} else {
			String img = uploadFile.fildUpload(file);
			store.setStoreImg(img);
			store.setStoreThumb(img);
		}
			 
		adminService.storeRegist(store);
		
		return "redirect:/admin/storeManagement";
	}
	
	
	
	@GetMapping("/admin/detail/{id}")
	public String adminStoreDetail(@PathVariable int id,Model model, @AuthenticationPrincipal LoginDetail user) throws NotFoundException {
		
		long userId = 0;
		if(user != null) {
			userId = user.getUser().getId();
			model.addAttribute("adminPage" , true);
		}
		
		StoreDetail storeDetail = storeService.storeDetail(id, userId);

		model.addAttribute("store", storeDetail);
		
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
	public String addMenu(Food food, String[] foodOption, Integer[] foodOptionPrice, MultipartFile file) throws IOException {
		
//		이미지 첨부 x 
		if(file.isEmpty()) {
			String img = File.separator + "img" + File.separator + "none.gif";
			food.setFoodImg(img);
			food.setFoodThumb(img);
		} 
//		이미지 첨부 o 
		else {
			String img = uploadFile.fildUpload(file);
			food.setFoodImg(img);
			food.setFoodThumb(img);
		}
		adminService.addMenu(food, foodOption, foodOptionPrice);
		return "redirect:/admin/detail/" + food.getStoreId() ;
	}
	
	
	
	
	@PostMapping("/admin/menuModify")
	public String modifyMenu(Food food, String[] foodOption, Integer[] foodOptionPrice, Integer[] optionId, MultipartFile file) throws IOException {
		if(file.isEmpty()) {
			String img = File.separator + "img" + File.separator + "none.gif";
			food.setFoodImg(img);
			food.setFoodThumb(img);
		} else {
			String img = uploadFile.fildUpload(file);
			food.setFoodImg(img);
			food.setFoodThumb(img);
		}
		
		adminService.modifyMenu(food, foodOption, foodOptionPrice, optionId);
		
		return "redirect:/admin/detail/" + food.getStoreId();
	}	
	
	
	@ResponseBody
	@DeleteMapping("/admin/option")
	public void deleteOPtion(long optionId) {
		adminService.deleteOption(optionId);
	}
	
	
	
	@PostMapping("/admin/storeModify")
	public String storeModify(Store store, MultipartFile file) throws IOException {
		
		if(!file.isEmpty()){
			String img = uploadFile.fildUpload(file);
			store.setStoreImg(img);
		}
		
		adminService.storeModify(store);
		return "redirect:/admin/detail/" + store.getId();
	}
	
	
	@ResponseBody
	@PatchMapping("/admin/orderAccept")
	public ResponseEntity<String> orderAccept(String orderNum, int time, long userId) {
//		userId == 0 비회원
		adminService.orderAccept(orderNum, time, userId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ResponseBody
	@PatchMapping("/admin/orderCancle")
	public ResponseEntity<String> orderCancle(OrderList orderList, String cancleReason) throws IOException, ParseException {
//		userId == 0 비회원
		adminService.orderCancle(orderList, cancleReason);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ResponseBody
	@PatchMapping("/admin/orderComplete")
	public ResponseEntity<String> orderComplete(String orderNum, long userId) {
//		userId == 0 비회원
		
		int result = adminService.orderComplete(orderNum, userId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@ResponseBody
	@PostMapping("/admin/pointRegist")
	public Map<String, Object> pointRegist(String giftCardNum, @AuthenticationPrincipal LoginDetail user, HttpSession session,  RedirectAttributes rttr) throws ServletException, IOException {
		Map<String, Object> point = adminService.pointRegist(giftCardNum, user.getUser().getId());
		if(point != null) {
			UserInfoSessionUpdate.sessionUpdate(point.get("point").toString(), "point", user, session);
			return point;
		} else {
			return null;
		}
		 
	}
	
	
	@GetMapping("/admin/salesManagement")
	public String sales() {
		
		return "admin/sales";
	}
	
	@ResponseBody
	@GetMapping("/admin/sales")
	public List<Sales> sales(String time,String month) {
//		time =
//		week, month, outerMonth, year
		
		List<Sales> sales = adminService.sales(time,month);
		return sales;
	}
	
	
	
	

}
