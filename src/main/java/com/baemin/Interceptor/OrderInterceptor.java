package com.baemin.Interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

public class OrderInterceptor  implements HandlerInterceptor {

	private static final Logger LOGGER = LogManager.getLogger(OrderInterceptor.class);
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		
//		
//		HttpSession session = request.getSession();
//		
//		CartDetail cart = (CartDetail) session.getAttribute("cart");
//		
//		if(cart == null) {
//			LOGGER.info("주문페이지");
//			response.sendRedirect("/");
//		}
//		
//		return HandlerInterceptor.super.preHandle(request, response, handler);
//	}
//	
//	

	
}
