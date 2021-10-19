package com.baemin.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baemin.config.LoginDetail;
import com.baemin.service.OrderService;
import com.baemin.service.StoreService;
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
	public String orderProc(HttpSession session, long total) {
		
		Map cartMap = (Map) session.getAttribute("cartMap");
		long orderPriceCheck = orderService.orderPriceCheck(cartMap);
		
		if(total == orderPriceCheck) {
			return "redirect:/paymentPage";
		} else {
			System.out.println("주문금액 오류");
			return "redirect:/";
		}
	}
	
	@ResponseBody
	@GetMapping("/paymentPage")
	public String paymentPage(HttpSession session, @AuthenticationPrincipal LoginDetail user ) {
		
		Map cartMap = (Map)session.getAttribute("cartMap");
		
		Gson gson = new Gson();
		
		String s = gson.toJson(cartMap);
		
		System.out.println(s);
		
		orderService.payment(cartMap, user);
		
		
		
		
		
		
		return "결제페이지";
	}
	
	
	
	
	
	
	
}
