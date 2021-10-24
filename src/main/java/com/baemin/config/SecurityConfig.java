package com.baemin.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	
	// 내가 인코딩하는게 아니라, 어떤 인코딩으로 패스워드가 만들어졌는지 알려주는 거야!!
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encodePwd());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		
		http.authorizeRequests().
			antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().permitAll()
		.and()
			.formLogin()
			.loginPage("/")
			.loginProcessingUrl("/login")
			.failureHandler(new AuthenticationFailureHandler() {
			      @Override
			      public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
			          System.out.println("AuthenticationFailureHandler");
//			          response.sendRedirect("/myPage");
			          String username = request.getParameter("username");
			          String password = request.getParameter("password");
			          
			          
			          System.out.println(username);
			          System.out.println(password);
			          System.out.println(exception);
			          
			          System.out.println(exception instanceof BadCredentialsException);
			          System.out.println(exception instanceof InternalAuthenticationServiceException);
			          
			           if(exception instanceof BadCredentialsException ||
			              exception instanceof InternalAuthenticationServiceException ) {
			        	   
			        	   request.setAttribute("msg" , "아이디와 비밀번호를 확인해 주세요");
			           }

			          request.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(request, response);
			      }
			 })
			.and()
			.logout()
			.logoutSuccessUrl("/myPage")
		
		;
		http.rememberMe()
			.key("uniqueAndSecret")
			.rememberMeParameter("remember-me")
			.tokenValiditySeconds(60 * 60 * 24 * 7)
			.userDetailsService(userDetailsService)
			;
		
//		http.authorizeRequests().
//		anyRequest().
//		permitAll()
//		;
		
//		http.cors().disable()
//        .csrf().disable()
//        .formLogin().disable()
//        .headers().frameOptions().disable();
		
		
		
//		super.configure(http);
//		
//		http.authorizeRequests()
//		.antMatchers("/user/**").authenticated() // 로그인만 하면 들어갈수 있는 주소
//		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
//		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//		.anyRequest().permitAll()  // 위 등록된 주소가 아니면 다 허용
//		.and()
//			.formLogin()
//			.loginPage("/auth/login") //권한 없는 사람이 요청했을땐 로그인페이지로 이동
//			.loginProcessingUrl("/auth/loginProc") // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 해줌
//			.defaultSuccessUrl("/") // 성공시 / 페이지로 이동
//		.and()
//			.oauth2Login()
//			.loginPage("/auth/loginProc")
//			.defaultSuccessUrl("/home") // 성공시 / 페이지로 이동
//			.userInfoEndpoint()
//			.userService(oauth2UserService)
//			
//			;
	
		
	}

	
	
	
}
