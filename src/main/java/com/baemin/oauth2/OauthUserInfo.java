package com.baemin.oauth2;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.baemin.vo.User;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Service
public class OauthUserInfo {
	
	
	@Autowired
	private BCryptPasswordEncoder encodePwd;
	
	
	public User oauthUserInfo(String provider, String username, OAuth2User oauth2user) {
		
		String password = encodePwd.encode("랜덤"); 
		String email = "";
		String phone = "";
		switch (provider) {

		case "google":
			email = oauth2user.getAttribute("email");
			phone = oauth2user.getAttribute("phone") == null ? "" : oauth2user.getAttribute("phone");
			break;

		case "naver":
			Map<String, Object> response = oauth2user.getAttribute("response");
			email = (String) response.get("email");
			phone = (String) response.get("mobile") == null ? "" : (String) response.get("mobile");
			break;
			
		case "kakao":
			Map<String, Object> kakaoAccount = oauth2user.getAttribute("kakao_account");
			email = (String) kakaoAccount.get("email");
			phone = oauth2user.getAttribute("phone") == null ? "" : oauth2user.getAttribute("phone");
		}
		
		
		phone = phone.replace("-", "");
		
		return new User(username, password, email, username, phone);
	}
	
	public String getOauthUsername(String provider,OAuth2User oauth2user) {
		String id = "";
		switch (provider) {

		case "google":
			id = oauth2user.getAttribute("sub");
			break;

		case "naver":
			Map<String, Object> response = oauth2user.getAttribute("response");
			id = (String) response.get("id");
			break;
			
		case "kakao":
			id = oauth2user.getAttribute("id").toString();
			break;
		}
		return provider + "_" + id;
		
	}

}
