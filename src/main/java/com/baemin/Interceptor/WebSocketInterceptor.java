package com.baemin.Interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.baemin.config.LoginDetail;
import com.nimbusds.jose.proc.SecurityContext;

public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		System.out.println("인터셉터 beforeHandshake");
		
		 ServletServerHttpRequest ssreq = (ServletServerHttpRequest) request;
	  
	        HttpServletRequest req =  ssreq.getServletRequest();
	        HttpSession session = req.getSession();
	        
	        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginDetail);
	        System.out.println("123" + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	        
	        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginDetail) {
	        	
	        	LoginDetail user = (LoginDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        	if(user.getUser().getRole().equals("ROLE_ADMIN")) {
	        		attributes.put("admin", user.getUser());
	        	}
	        }
	        


		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		System.out.println("인터셉터 afterHandshake");
		
	}
	
	

}
