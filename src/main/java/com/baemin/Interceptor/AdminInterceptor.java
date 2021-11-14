package com.baemin.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.baemin.config.LoginDetail;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

public class AdminInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		System.out.println(session);
		
		LoginDetail loginDetail = (LoginDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String role = loginDetail.getUser().getRole();
		
		if(!role.equals("ROLE_ADMIN")) {
			System.out.println();
			
			response.sendRedirect("/");
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
}
