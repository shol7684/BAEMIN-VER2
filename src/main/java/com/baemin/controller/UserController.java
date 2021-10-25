package com.baemin.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baemin.config.LoginDetail;
import com.baemin.service.UserService;
import com.baemin.vo.Point;
import com.baemin.vo.Review;
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
//		valeuType = username, nickname
		int count = userService.overlapCheck(value, valueType);
		return count;
	}
	
	@GetMapping("/user/myReview")
	public String myreView(@AuthenticationPrincipal LoginDetail user, Model model) {
		
		long id = user.getUser().getId(); 
		
		List<Review> myReviewList = userService.myReviewList(id);
		
		model.addAttribute("myReviewList", myReviewList);
		
		return "user/myReview";
	}
	
	@ResponseBody
	@DeleteMapping("/user/review")
	public void deleteReview(String orderNum, long id, @AuthenticationPrincipal LoginDetail user) {
		
		// 로그인 한 사용자와 리뷰 쓴 사람 아이디가 같을때
		if(user.getUser().getId() == id) {
			userService.deleteReview(orderNum);
		}
	}
	
	
	@GetMapping("/user/point")
	public String myPoint(@AuthenticationPrincipal LoginDetail user, Model model) {
		
		if(user != null) {
			long id = user.getUser().getId();
			List<Point> myPoint =  userService.myPoint(id);
			model.addAttribute("myPoint", myPoint);
			model.addAttribute("point", user.getUser().getPoint());
			
		}
		
		
		return "user/point";
	}
	
	
	
	

}
