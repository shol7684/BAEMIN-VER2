package com.baemin.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
	
	private static final Logger LOGGER = LogManager.getLogger(MainController.class);

	@GetMapping("/")
	public String main(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
		
		LOGGER.info("main");

		if(session.getAttribute("BMaddress1") == null || session.getAttribute("BMaddress2") == null) {
			
			Cookie[] cookies = request.getCookies();
			
			if(cookies == null) {
				return "home";
			}
			
			for(int i=0;i<cookies.length;i++) {
				
				if(cookies[i].getName().equals("BMaddress1")) {
					session.setAttribute("BMaddress1", cookies[i].getValue());
				}
				if(cookies[i].getName().equals("BMaddress2")) {
					session.setAttribute("BMaddress2", URLDecoder.decode(cookies[i].getValue() , "UTF-8"));
				}
			}
		}
		
		
		
		return "home";
	}
	
	@PostMapping("/")
	public String main(String address1, String address2, HttpServletResponse response) throws UnsupportedEncodingException {
//		address1 = 우편번호
//		address2 = 주소
		
		Cookie cookie1 = new Cookie("BMaddress1" , address1);
		Cookie cookie2 = new Cookie("BMaddress2" , URLEncoder.encode(address2, "UTF-8"));
		
		int age = 60 * 60 * 24 * 7;
		cookie1.setMaxAge(age);
		cookie2.setMaxAge(age);
		
		response.addCookie(cookie1);
		response.addCookie(cookie2);
		
		return "redirect:/";
	}
	
	

}
