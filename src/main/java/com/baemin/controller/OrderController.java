package com.baemin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baemin.config.LoginDetail;
import com.baemin.service.OrderService;
import com.baemin.service.Payment;
import com.baemin.util.CreateOrderNum;
import com.baemin.util.FoodInfoFromJson;
import com.baemin.util.Page;
import com.baemin.vo.CartDetail;
import com.baemin.vo.OrderInfo;
import com.baemin.vo.OrderList;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;

	private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

	@Autowired
	private Payment payment;

	@ResponseBody
	@PostMapping("/order/payment-cash")
	public ResponseEntity<String> payment(HttpSession session, OrderInfo orderInfo, long totalPrice, @AuthenticationPrincipal LoginDetail user) throws IOException {
		
		CartDetail cartDetail = (CartDetail) session.getAttribute("cart");
		
		long orderPriceCheck = orderService.orderPriceCheck(cartDetail);
		
		System.out.println("계산금액 = " + totalPrice + " 실제 계산해야할 금액 = " + orderPriceCheck );
		
		if(orderPriceCheck == totalPrice) {
			orderService.order(cartDetail, orderInfo, user, session);
			session.removeAttribute("cart");
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
		System.out.println("amount = " + amount);

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

			CartDetail cartDetail = (CartDetail) session.getAttribute("cart");

			// 실제 계산 되어야할 가격
			long orderPriceCheck = orderService.orderPriceCheck(cartDetail) - usedPoint;

			if (orderPriceCheck != Long.parseLong(amount)) {
				LOGGER.info("결제 금액 오류");
				// 결제 취소
				payment.payMentCancle(accessToken, imp_uid, amount, "결제 금액 오류");
				return new ResponseEntity<String>("결제 금액 오류, 결제 취소", HttpStatus.BAD_REQUEST);

			}

			String orderNum = orderService.order(cartDetail, orderInfo, user, session);
			session.removeAttribute("cart");

			return new ResponseEntity<String>("주문이 완료되었습니다", HttpStatus.OK);

		} catch (Exception e) {
			LOGGER.info("결제에러");

			payment.payMentCancle(accessToken, imp_uid, amount, "결제에러");

			return new ResponseEntity<String>("결제에러", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/order")
	public String order(HttpSession session, Model model, @AuthenticationPrincipal LoginDetail user) {

		CartDetail cartDetail = (CartDetail) session.getAttribute("cart");

		model.addAttribute("cart", cartDetail);

		System.out.println(user);

		if (user != null) {
			model.addAttribute("user", user.getUser());
		}
		String orderNum = CreateOrderNum.createOrderNum();
		model.addAttribute("orderNum", orderNum);
		return "order/order";
	}

//	@PostMapping("/order")
//	public String orderProc(HttpSession session, long total, OrderInfo info, @AuthenticationPrincipal LoginDetail user,
//			RedirectAttributes rttr) {
//
//		CartDetail cartDetail = (CartDetail) session.getAttribute("cart");
//
//		long orderPriceCheck = orderService.orderPriceCheck(cartDetail);
//
//		if (total != orderPriceCheck) {
//			LOGGER.info("주문금액 오류");
//			return "redirect:/";
//		}
//
////		결제 서비스 마지막에 구현
////		orderService.payment();
//
//		String orderNum = orderService.order(cartDetail, info, user, session);
//		rttr.addFlashAttribute("orderMessage", "주문이 완료되었습니다");
//		rttr.addFlashAttribute("orderNum", orderNum);
//		session.removeAttribute("cart");
//
//		return "redirect:/";
//	}

	@GetMapping({ "/orderList", "/orderList/{movePage}" })
	public String orderList(@AuthenticationPrincipal LoginDetail user, Model model,
			@PathVariable Optional<Integer> movePage) {
		if (user == null) {
			System.out.println("비로그인");
		} else {
			System.out.println("로그인");
			long userId = user.getUser().getId();

			Page p = new Page(movePage, 20);

			List<OrderList> orderList = orderService.orderList(userId, p.getPageStart(), p.getPageEnd());

			if (orderList.size() == 0) {
				return "order/orderList";
			}

			model.addAttribute("user", user.getUser());
			model.addAttribute("page", p);

			List<Map<String, Object>> deleveryInfo = new ArrayList<>();

			for (int i = 0; i < orderList.size(); i++) {
				deleveryInfo.add(FoodInfoFromJson.foodInfoFromJson(orderList.get(i)));
			}

			model.addAttribute("lastPage", p.lastPage(orderList.get(0).getListCount()));
			model.addAttribute("deleveryInfo", deleveryInfo);

		}

		return "order/orderList";
	}

	@GetMapping("/orderListDetail/{orderNum}")
	public String orderDetail(@PathVariable String orderNum, Model model, @AuthenticationPrincipal LoginDetail user) {

		OrderList orderListDetail = orderService.orderListDetail(orderNum);

		if (user != null && (user.getUser().getId() != orderListDetail.getUserId())) {
			System.out.println("다른 사용자");
			return "redirect:/";
		} else if (user == null) {
			System.out.println("비로그인");
			return "redirect:/";
		}

		Map<String, Object> detail = FoodInfoFromJson.foodInfoFromJson(orderListDetail);

		model.addAttribute("orderListDetail", detail.get("orderListDetail"));
		model.addAttribute("cart", detail.get("cart"));
		model.addAttribute("amount", detail.get("amount"));

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
