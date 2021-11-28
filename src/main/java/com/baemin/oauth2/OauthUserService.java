package com.baemin.oauth2;

import java.io.IOException;
import java.util.Map;

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

import com.baemin.config.LoginDetail;
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
	private OauthUserInfo oauthUserInfo;
	
	private static final Logger LOGGER = LogManager.getLogger(OauthUserService.class);

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oauth2user = super.loadUser(userRequest);
		String provider = userRequest.getClientRegistration().getRegistrationId();
		String username = oauthUserInfo.getOauthUsername(provider, oauth2user);
 		User user = sql.selectOne("user.login" , username);
		
		if(user == null) {
			user = oauthUserInfo.oauthUserInfo(provider, username, oauth2user);
			userDAO.join(user);
			
			// id = selectKey	
			// 회원가입후 재로그인 하기 전까지 role이 null
			user.setRole("ROLE_USER");
			LOGGER.info("oauth2 회원가입" +  provider);

		}
		LoginDetail loginDetail = new LoginDetail();
		loginDetail.setUser(user);
		loginDetail.setOauth2User(oauth2user.getAttributes());
		 
		return loginDetail;
	}

}
