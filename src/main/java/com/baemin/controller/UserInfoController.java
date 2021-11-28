package com.baemin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baemin.service.MailService;
import com.baemin.service.UserService;
import com.baemin.util.AuthNum;

@Controller
public class UserInfoController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encodePwd;
	
	@Autowired
	private MailService mailService;
	
	
	@GetMapping("/find/id")
	public String findId() {
		return "user/userInfo/findId";
	}
	
	@ResponseBody
	@PostMapping("/find/id/sendEmail")
	public void sendEmail(String email) {
		List<String> usernames = userService.findId(email);
		
		if(usernames.size() != 0) {
			new Thread(new Runnable() {
				public void run() {
					mailService.sendUsernames(email, usernames);
				}
			}).start();
		}
	}
	
	
	
	
	
	@GetMapping("/find/password")
	public String findPassword() {
		return "user/userInfo/findPassword";
	}
	
	
	
	
	@GetMapping("/find/password/auth")
	public String passwordAuth(String username, Model model) {
		model.addAttribute("username", username);
		return "user/userInfo/passwordAuth";
	}
	
	
	
	
	@GetMapping("/find/password/auth/modify")
	public String passwordAuthModify(String authNum, String userId, String uuid, Model model) {
		model.addAttribute("userId", userId);
		model.addAttribute("uuid", uuid);
		return "user/userInfo/passwordModify";
	}
	
	
	@ResponseBody
	@GetMapping("/authEmail")
	public Map<String, Object> authEmail(String email, String username, HttpSession session) {
		System.out.println(email);
		System.out.println(username);
		
		//username의 email일 맞는지 확인
		String userId = userService.authCheck(username, email, "email");
		
		if(userId == null) {
			return null;
		}

		AuthNum at = new AuthNum();
		
		String authNum = at.createAuthNum();
		int validity = at.saveAuthNum(authNum, session);
		
		new Thread(new Runnable() {
			public void run() {
				mailService.sendAuthNum(email, authNum);
			}
		}).start();
		
		Map<String, Object > map = new HashMap<>();
		map.put("validityTime", validity);
		map.put("userId", userId);
		
		return map;
	}
	
	@ResponseBody
	@GetMapping("/authPhone")
	public Map<String, Object> authPhone(String phone, String username, HttpSession session) {
		System.out.println(phone);
		System.out.println(username);
		
		//username의 phone 맞는지 확인
		String userId = userService.authCheck(username, phone, "phone");
		
		if(userId == null) {
			return null;
		}
		
		AuthNum at = new AuthNum();
		
		String authNum = at.createAuthNum();
		int validity = at.saveAuthNum(authNum, session);
		
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("validityTime", validity);
		map.put("userId", userId);
		
		return map;
	}
	
	
	
	
	@ResponseBody
	@GetMapping("/authNumCheck")
	public Map<String, String> authNumCheck(String authNum, HttpSession session) {
		Map<String, String> map = new HashMap<>();
		System.out.println();
		System.out.println(authNum);
		Map<String, Object> sessionAuth =  (Map<String, Object>) session.getAttribute("authNumSession"); 
		
		System.out.println("sessionAuth = " + sessionAuth);
		
		if(sessionAuth == null) {
			map.put("status", "fail");
			map.put("msg", "인증시간이 만료되었습니다");
			return map;
		}
		
		
		long currentTime = System.currentTimeMillis();
		long sessionTime = (long) sessionAuth.get("currentTime");
		int validity = (int) sessionAuth.get("validity");
		String sessionAuthNum = (String) sessionAuth.get("authNum");
		
		if(currentTime - sessionTime > validity * 1000) {
			map.put("status", "fail");
			map.put("msg", "인증시간이 만료되었습니다");
			return map;
		}
		
		
		if(!sessionAuthNum.equals(authNum)) {
			map.put("status", "fail");
			map.put("msg", "인증번호가 일치하지 않습니다");
			return map;
		}
		
		String uuid = UUID.randomUUID().toString();
		
		session.setMaxInactiveInterval(300);
		session.setAttribute("authUUID", uuid);
		
		map.put("status", "success");
		map.put("uuid", uuid);
		
		return map;
	}
	
	
	
	
	@ResponseBody
	@PatchMapping("/modifyPassword")
	public String modifyPassword(String password, String uuid, String userId, HttpSession session) {
		String sessionUUID = (String) session.getAttribute("authUUID");
		
		if(sessionUUID == null ) {
			return "인증시간이 만료되었습니다";
		}
		
		if(!sessionUUID.equals(uuid)) {
			return "인증 오류";
		}
		password = encodePwd.encode(password);
		userService.modifyPassword(Long.parseLong(userId), password);
		session.removeAttribute("authUUID");
		
		return "변경되었습니다";
	}

}
