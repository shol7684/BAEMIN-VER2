package com.baemin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baemin.config.LoginDetail;
import com.baemin.service.OrderService;
import com.baemin.service.PaymentServiceImp;
import com.baemin.util.CreateOrderNum;
import com.baemin.util.FoodInfoFromJson;
import com.baemin.util.Page;
import com.baemin.vo.Cart;
import com.baemin.vo.CartList;
import com.baemin.vo.OrderInfo;
import com.baemin.vo.OrderList;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

	@Autowired
	private PaymentServiceImp payment;

	@ResponseBody
	@PostMapping("/order/payment-cash")
	public ResponseEntity<String> payment(HttpSession session, OrderInfo orderInfo, long totalPrice, @AuthenticationPrincipal LoginDetail user) throws IOException {
		
		CartList cartList = (CartList) session.getAttribute("cartList");
		
		long orderPriceCheck = orderService.orderPriceCheck(cartList);
		
		System.out.println("계산금액 = " + totalPrice + " 실제 계산해야할 금액 = " + orderPriceCheck );
		
		if(orderPriceCheck == totalPrice) {
			orderService.order(cartList, orderInfo, user, session);
			session.removeAttribute("cartList");
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/order/payment/complete")
	public ResponseEntity<String> payment(String imp_uid, String merchant_uid, HttpSession session, OrderInfo orderInfo,
			@AuthenticationPrincipal LoginDetail user) throws IOException, ParseException {
		// imp_uid 아임포트 주문시 고유 아이디
		// 주문번호 merchant_uid, OrderNum
		String accessToken = payment.getToken();
		// 결제된 가격 가져오기
		String amount = payment.paymentInfo(imp_uid, accessToken);

		try {
			int usedPoint = orderInfo.getUsedPoint();

			if (user != null) {
				long point = user.getUser().getPoint();

				if (point < usedPoint) {
					LOGGER.info("유저 포인트 오류");
					payment.payMentCancle(accessToken, imp_uid, amount, "유저 포인트 오류");
					return new ResponseEntity<String>("유저 포인트 오류", HttpStatus.BAD_REQUEST);
				}

			} else {
				if (usedPoint != 0) {
					LOGGER.info("비회원 포인트사용 오류");
					payment.payMentCancle(accessToken, imp_uid, amount, "비회원 포인트사용 오류");
					return new ResponseEntity<String>("비회원 포인트 사용 오류", HttpStatus.BAD_REQUEST);
				}
			}

			CartList cartList = (CartList) session.getAttribute("cartList");

			// 실제 계산 되어야할 가격
			long orderPriceCheck = orderService.orderPriceCheck(cartList) - usedPoint;

			if (orderPriceCheck != Long.parseLong(amount)) {
				LOGGER.info("결제 금액 오류");
				// 결제 취소
				payment.payMentCancle(accessToken, imp_uid, amount, "결제 금액 오류");
				return new ResponseEntity<String>("결제 금액 오류, 결제 취소", HttpStatus.BAD_REQUEST);
			}
			orderInfo.setImpUid(imp_uid);
			orderService.order(cartList, orderInfo, user, session);
			session.removeAttribute("cartList");

			return new ResponseEntity<String>("주문이 완료되었습니다", HttpStatus.OK);

		} catch (Exception e) {
			LOGGER.info("결제에러");

			payment.payMentCancle(accessToken, imp_uid, amount, "결제에러");

			return new ResponseEntity<String>("결제에러", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/order")
	public String order(HttpSession session, Model model, @AuthenticationPrincipal LoginDetail user) {

		CartList cartList = (CartList) session.getAttribute("cartList");

		model.addAttribute("cartList", cartList);

		System.out.println(user);

		if (user != null) {
			model.addAttribute("user", user.getUser());
		}
		String orderNum = CreateOrderNum.createOrderNum();
		model.addAttribute("orderNum", orderNum);
		return "order/order";
	}


	
	
	@GetMapping({ "/orderList", "/orderList/{page}" })
	public String orderList(@AuthenticationPrincipal LoginDetail user, Model model,
			@PathVariable Optional<Integer> page) {
		if (user == null) {
			System.out.println("비로그인");
		} else {
			System.out.println("로그인");
			long userId = user.getUser().getId();

			Page p = new Page(page, 20);

			List<OrderList> orderList = orderService.orderList(userId, p);

			if (orderList.size() == 0) {
				return "order/orderList";
			}

			model.addAttribute("user", user.getUser());
			model.addAttribute("page", p);

			List<List<Cart>> cartList = new ArrayList<>();

			for (int i=0;i<orderList.size();i++) {
				cartList.add(FoodInfoFromJson.foodInfoFromJson(orderList.get(i).getFoodInfo()));
			}
			
			
			model.addAttribute("lastPage", p.lastPage(orderList.get(0).getListCount()));
			model.addAttribute("cartList", cartList);
			model.addAttribute("orderList", orderList);

		}

		return "order/orderList";
	}

	@GetMapping("/orderListDetail/{orderNum}")
	public String orderDetail(@PathVariable String orderNum, Model model, @AuthenticationPrincipal LoginDetail user) {

		OrderList orderDetail = orderService.orderListDetail(orderNum);
		System.out.println("orderListdetail = " + orderDetail);
		if (user != null && (user.getUser().getId() != orderDetail.getUserId())) {
			System.out.println("다른 사용자");
			return "redirect:/";
		} else if (user == null) {
			System.out.println("비로그인");
			return "redirect:/";
		}
		
		List<Cart> list = FoodInfoFromJson.foodInfoFromJson(orderDetail.getFoodInfo());

		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute("cart", list);

		return "order/orderListDetail";
	}


}
