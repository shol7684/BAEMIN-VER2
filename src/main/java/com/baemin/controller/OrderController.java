package com.baemin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMethodMappingNamingStrategy;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baemin.config.LoginDetail;
import com.baemin.service.OrderService;
import com.baemin.util.FoodInfoFromJson;
import com.baemin.util.Page;
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
	public String orderProc(HttpSession session, long total, OrderInfo info, @AuthenticationPrincipal LoginDetail user, RedirectAttributes rttr) {
		
		Map cartMap = (Map) session.getAttribute("cartMap");
		long orderPriceCheck = orderService.orderPriceCheck(cartMap);
		
		if(total != orderPriceCheck) {
			LOGGER.info("주문금액 오류");
			return "redirect:/";
		}  
		
		if(user != null) {
			long point = user.getUser().getPoint();
			if(point < info.getUsedPoint()) {
				LOGGER.info("유저 포인트 오류");
				rttr.addFlashAttribute("orderMessage", "포인트 사용 오류");
				return "redirect:/";
			}
		} else {
			if(info.getUsedPoint() != 0) {
				LOGGER.info("비회원 포인트사용 오류");
				rttr.addFlashAttribute("orderMessage", "포인트 사용 오류");
				return "redirect:/";
			}
		}
		
//		결제 서비스 마지막에 구현
//		orderService.payment();
		
		
		String orderNum = orderService.order(cartMap,info, user, session);
		rttr.addFlashAttribute("orderMessage", "주문이 완료되었습니다");
		rttr.addFlashAttribute("orderNum", orderNum);
		session.removeAttribute("cartMap");
		
		return "redirect:/";
	}
	
	
	@GetMapping({"/orderList", "/orderList/{movePage}"})
	public String orderList(@AuthenticationPrincipal LoginDetail user, Model model, @PathVariable Optional<Integer> movePage ) {
		if(user == null) {
			System.out.println("비로그인");
		} else {
			System.out.println("로그인");
			
			Page p = new Page(movePage);
			
			model.addAttribute("page", p);
			
			long userId = user.getUser().getId();
			
			model.addAttribute("user", user.getUser());
			
			List<OrderList> orderList = orderService.orderList(userId, p.getPageStart(), p.getPageEnd());
			
			List<Map<String, Object>> deleveryInfo = new ArrayList<>();
			
			for(int i=0;i<orderList.size();i++) {
				deleveryInfo.add(FoodInfoFromJson.foodInfoFromJson(orderList.get(i)));
			}
			model.addAttribute("lastPage", p.lastPage(orderList.get(0).getListCount()));
			model.addAttribute("deleveryInfo", deleveryInfo);
			
			
			
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
		
		Map<String, Object> detail = FoodInfoFromJson.foodInfoFromJson(orderListDetail);
		
		model.addAttribute("orderListDetail" , detail.get("orderListDetail"));
		model.addAttribute("cart" , detail.get("cart"));
		model.addAttribute("amount" , detail.get("amount"));
		
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
