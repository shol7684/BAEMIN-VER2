package com.baemin.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import com.baemin.controller.AdminController;
import com.baemin.dao.UserDAO;
import com.baemin.vo.User;


@Service
public class OauthUserService extends DefaultOAuth2UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SqlSession sql;
	
	@Autowired
	private BCryptPasswordEncoder encodePwd;
	
	private static final Logger LOGGER = LogManager.getLogger(OauthUserService.class);

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		System.out.println();
		System.out.println("getClientRegistration = " + userRequest.getClientRegistration());
		System.out.println();
		System.out.println("getProviderDetails = " + userRequest.getClientRegistration().getProviderDetails());
		System.out.println();
		System.out.println("super.loadUser(userRequest) = " + super.loadUser(userRequest).getAttributes());
		System.out.println();
		OAuth2User oauth2user = super.loadUser(userRequest);
		System.out.println();
		System.out.println("oauth2user = " + oauth2user);
		
		
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String id = "";
		
		switch (registrationId) {
		
		case "google":
			id = oauth2user.getAttribute("sub");
			break;

		case "facebook": 
			id = oauth2user.getAttribute("id");
			break;
		}
		
		String username = registrationId + "_" + id;
		User user = sql.selectOne("user.login" , username);
		
		
		if(user == null) {
			String password = encodePwd.encode("랜덤"); 
			String email = oauth2user.getAttribute("email");
			String phone = oauth2user.getAttribute("phone") == null ? "" : oauth2user.getAttribute("phone");
			
			user = new User(username, password, email, username, phone);
			
			userDAO.join(user);
			
			// id = selectKey	
			// 회원가입후 재로그인 하기 전까지 role이 null
			user.setRole("ROLE_USER");
			
			LOGGER.info("oauth2 회원가입" +  registrationId);

		}
		LoginDetail loginDetail = new LoginDetail();
		loginDetail.setUser(user);
		loginDetail.setOauth2User(oauth2user.getAttributes());
		 
		return loginDetail;
	}

}
