package com.baemin.config;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

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
	

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		System.out.println("getClientRegistration = " + userRequest.getClientRegistration());
		System.out.println("getProviderDetails = " + userRequest.getClientRegistration().getProviderDetails());
		
		
		System.out.println("super.loadUser(userRequest) = " + super.loadUser(userRequest).getAttributes());
		
		OAuth2User oauth2user = super.loadUser(userRequest);
		
		System.out.println("oauth2user = " + oauth2user);
		
		String probider = userRequest.getClientRegistration().getRegistrationId();
		String probiderId = oauth2user.getAttribute("sub");
		String username = probider + "_" + probiderId;
		
		User user = sql.selectOne("user.login" , username);
		System.out.println("user1 = " + user);
		
		
		if(user == null) {
			String password = encodePwd.encode("랜덤"); 
			String email = oauth2user.getAttribute("email");
			String phone = oauth2user.getAttribute("phone") == null ? "" : oauth2user.getAttribute("phone");
			
//			user = new User(username, password, email, username, phone);
			
			userDAO.join(user);
			
			// id = selectKey	
			// 회원가입후 재로그인 하기 전까지 role이 null
			user.setRole("ROLE_USER");
			
			System.out.println("user2 = " + user);
		}
		LoginDetail loginDetail = new LoginDetail();
		System.out.println("user3 = " + user);
		
		loginDetail.setUser(user);
		System.out.println("loginDetail1 = " + loginDetail);
		loginDetail.setOauth2User(oauth2user.getAttributes());
		
		System.out.println("loginDetail2 = " + loginDetail);
		
		 
		return loginDetail;
	}

}
