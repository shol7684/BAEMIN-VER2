package com.baemin.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

public class AuthNum {

	public String createAuthNum() {
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for(int i=0;i<4;i++) {
			sb.append(r.nextInt(9));
		}
		
		return sb.toString();
	}
	
	
	
	public int saveAuthNum(String authNum, HttpSession session) {
		int validity = 120;
		
		Map<String, Object> authNumSession  = new HashMap<>();
		authNumSession.put("authNum", authNum);
		authNumSession.put("currentTime", System.currentTimeMillis());
		authNumSession.put("validity", validity);
		
		session.setMaxInactiveInterval(validity);
		session.setAttribute("authNumSession", authNumSession);
		
		System.out.println(authNum);
		
		return validity;
	}
}
