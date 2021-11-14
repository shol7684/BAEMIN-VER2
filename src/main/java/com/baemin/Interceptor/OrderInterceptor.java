package com.baemin.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import com.baemin.controller.OrderController;
import com.baemin.vo.Cart;
import com.baemin.vo.CartDetail;

public class OrderInterceptor  implements HandlerInterceptor {

	private static final Logger LOGGER = LogManager.getLogger(OrderInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		HttpSession session = request.getSession();
		
		CartDetail cart = (CartDetail) session.getAttribute("cart");
		
		if(cart == null) {
			LOGGER.info("주문페이지");
			response.sendRedirect("/");
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	

	
}
