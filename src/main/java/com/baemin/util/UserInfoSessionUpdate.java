package com.baemin.util;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.baemin.config.LoginDetail;

public class UserInfoSessionUpdate {

	public static void sessionUpdate(String value, String valueType, LoginDetail user, HttpSession session) {

		LoginDetail loginDetail = (LoginDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(valueType.equals("nickname")) {
			loginDetail.getUser().setNickname(value);
		} 
		else  if(valueType.equals("password")) {
			loginDetail.getUser().setPassword(value);
		}
		else if(valueType.equals("point")) {
			long point = loginDetail.getUser().getPoint() + Long.parseLong(value);
			loginDetail.getUser().setPoint(point);
		}

		SecurityContext sc = SecurityContextHolder.getContext();

		sc.setAuthentication(new UsernamePasswordAuthenticationToken(loginDetail, null, user.getAuthorities()));
		
		session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);
	}
}
