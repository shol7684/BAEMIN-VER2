package com.baemin.controller;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baemin.config.LoginDetail;
import com.baemin.service.AdminService;
import com.baemin.service.StoreService;
import com.baemin.util.FoodInfoFromJson;
import com.baemin.util.Page;
import com.baemin.util.UserInfoSessionUpdate;
import com.baemin.vo.Food;
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
	public List<Map> orderList(String list) {

		System.out.println(list);
		
		List<OrderList> orderList = adminService.orderList(list);
		
		List<Map> deleveryInfo = new ArrayList<>();
		
		for(int i=0;i<orderList.size();i++) {
			deleveryInfo.add(FoodInfoFromJson.foodInfoFromJson(orderList.get(i)));
		}
		
		return deleveryInfo;
	}

	@GetMapping({"/admin/storeManagement", "/admin/storeManagement/{movePage}"})
	public String storeManagement(Model model, @PathVariable Optional<Integer> movePage) {

		Page p = new Page(movePage);
		
		System.out.println(p);
		
		List<Store> storeList = adminService.storeList(p);
		
			
		model.addAttribute("lastPage", p.lastPage(storeList.get(0).getListCount()));
		model.addAttribute("page", p);
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
	public ResponseEntity<String> orderAccept(String orderNum, int time, long userId) {
//		userId == 0 비회원
		int result = adminService.orderAccept(orderNum, time, userId);
		if(result == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@ResponseBody
	@PostMapping("/admin/orderCancle")
	public ResponseEntity<String> orderCancle(String orderNum, String cancleReason, long userId) {
//		userId == 0 비회원
		int result = adminService.orderCancle(orderNum, cancleReason, userId);
		
		if(result == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
				
	}
	
	
	
	
	@PostMapping("/admin/pointRegist")
	public String pointRegist(String giftCardNum, @AuthenticationPrincipal LoginDetail user, HttpSession session,  RedirectAttributes rttr) throws ServletException, IOException {
		int point = adminService.pointRegist(giftCardNum, user.getUser().getId());
		
		if(point != 0) {
			UserInfoSessionUpdate.sessionUpdate(point+"", "point", user, session);
			DecimalFormat fm = new DecimalFormat();
			rttr.addFlashAttribute("pointRegistMessage", fm.format(point) +"원 충전되었습니다");
		} else {
			rttr.addFlashAttribute("pointRegistMessage", "잘못된 번호입니다");
		}
		return "redirect:/user/point";
	}
	
	
	
	// 사용자가 주문시 실시간 주문요청 받기 웹소켓
	@ResponseBody
	@GetMapping("/admin/order-one")
	public Map getOrderone(String orderNum) {
		
		OrderList order = adminService.getOrderOne(orderNum);
		
		Map map = FoodInfoFromJson.foodInfoFromJson(order);
		return map;
	}
	

}
 