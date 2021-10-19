package com.baemin.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baemin.config.LoginDetail;
import com.baemin.service.UserService;
import com.baemin.vo.User;

@Controller
public class UserController {
	
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@GetMapping("/myPage")
	public String myPage(@AuthenticationPrincipal LoginDetail user ,Model model) {
		
		if(user != null) {
			model.addAttribute("username" ,user.getUsername());
		}
		
		
		
		
		
		return "user/myPage";
	}
	
	@GetMapping("/login")
	public String login() {
		
		
		return "user/login";
	}
	
	@GetMapping("/join")
	public String join() {
		
		return "user/join";
	}
	
	@PostMapping("/join")
	public String joinProc(User user) {
		
		String encPwd = pwdEncoder.encode(user.getPassword());
		user.setPassword(encPwd);
		userService.join(user);
		
		return "redirect:/login";
	}
	
	@ResponseBody
	@GetMapping("/overlapCheck")
	public int overlapCheck(String value, String valueType ) {
		
		System.out.println(value);
		System.out.println(valueType);
		
//		valeuType = username, nickname
		
		int count = userService.overlapCheck(value, valueType);
		
		return count;
	}

}
