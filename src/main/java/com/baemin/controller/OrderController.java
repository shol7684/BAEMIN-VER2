package com.baemin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.baemin.config.LoginDetail;
import com.baemin.service.OrderService;
import com.baemin.vo.Cart;
import com.baemin.vo.OrderInfo;
import com.baemin.vo.OrderList;
import com.google.gson.Gson;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	private static final Logger LOGGER = LogManager.getLogger(OrderController.class);
	
	@GetMapping("/order")
	public String order(HttpSession session, Model model, @AuthenticationPrincipal  LoginDetail user) {
		
		Map<String, Object> cartMap = (Map<String, Object>) session.getAttribute("cartMap");
		
		model.addAttribute("cartMap" , cartMap);
		
		System.out.println(cartMap);
		
		System.out.println(user);
		
		if(user != null) {
			model.addAttribute("user", user.getUser());
		}
		
		return "order/order";
	}
	
	@PostMapping("/order")
	public String orderProc(HttpSession session, long total, OrderInfo info, @AuthenticationPrincipal LoginDetail user ) {
		
		Map cartMap = (Map) session.getAttribute("cartMap");
		long orderPriceCheck = orderService.orderPriceCheck(cartMap);
		
		System.out.println("total = " + total);
		System.out.println("orderPriceCheck = " + orderPriceCheck);
		
		if(total != orderPriceCheck) {
			System.out.println("주문금액 오류");
			return "redirect:/";
		}  
		
		if(user != null) {
			long point = user.getUser().getPoint();
			if(point < info.getUsedPoint()) {
				LOGGER.info("유저 포인트 오류");
				return "redirect:/";
			}
		} else {
			if(info.getUsedPoint() != 0) {
				LOGGER.info("비회원 포인트사용 오류");
				return "redirect:/";
			}
		}
		
//		결제 서비스 마지막에 구현
//		orderService.payment();
		
		
		orderService.order(cartMap,info, user);
		
		session.removeAttribute("cartMap");
		
		return "redirect:/";
	}
	
	
	@GetMapping("/orderList")
	public String orderList(@AuthenticationPrincipal LoginDetail user, Model model ) {
		if(user == null) {
			System.out.println("비로그인");
		} else {
			System.out.println("로그인");
			
			long userId = user.getUser().getId();
			
			List<OrderList> orderList = orderService.orderList(userId);
			
			System.out.println(orderList);
			
			List<Map> cart = new ArrayList<>();
			
			Gson gson = new Gson();
			for(int i=0;i<orderList.size();i++) {
//				cart.add(gson.fromJson(orderList.get(i).getOrderInfo(), Map.class));
			}
			
			System.out.println(cart);
			
			model.addAttribute("orderList", orderList);
//			model.addAttribute("cart", cart);
			
			
			
		}
		
		return "order/orderList";
	}
	
	
	@GetMapping("/orderListDetail/{orderNum}")
	public String orderDetail(@PathVariable String orderNum, Model model,@AuthenticationPrincipal LoginDetail user) {
		
		OrderList orderListDetail = orderService.orderListDetail(orderNum);
		
		if(user != null && (user.getUser().getId() != orderListDetail.getUserId())) {
			System.out.println("다른 사용자");
			return "redirect:/";
		} else if(user == null ) {
			System.out.println("비로그인");
			return "redirect:/";
		}
		
		String foodInfo = orderListDetail.getFoodInfo();
		
		String[] foodArr = foodInfo.split("},");
		Cart[] cart = new Cart[foodArr.length];
		
		Gson gson = new Gson();
		
		for(int i=0;i<foodArr.length;i++) {
			if(!foodArr[i].endsWith("}")) {
				cart[i] = gson.fromJson(foodArr[i] + "}" , Cart.class);
			} else {
				cart[i] = gson.fromJson(foodArr[i], Cart.class);
			}
		}
		
		
		int[] amount = gson.fromJson(orderListDetail.getAmount(), int[].class);
		
		model.addAttribute("orderListDetail" , orderListDetail);
		model.addAttribute("cart" , cart);
		model.addAttribute("amount" , amount);
		
		return "order/orderListDetail";
	}
	
	
	
	
//	@ResponseBody
//	@GetMapping("/paymentPage")
//	public String paymentPage(HttpSession session, @AuthenticationPrincipal LoginDetail user ) {
//		
//		Map cartMap = (Map)session.getAttribute("cartMap");
//		
//		Gson gson = new Gson();
//		
//		String s = gson.toJson(cartMap);
//		
//		System.out.println(s);
//		
//		orderService.payment(cartMap, user);
//		
//		return "결제페이지";
//	}
	
	
	
	
	
	
	
}
