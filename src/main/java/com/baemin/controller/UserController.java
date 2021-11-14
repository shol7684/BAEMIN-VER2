package com.baemin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baemin.config.LoginDetail;
import com.baemin.service.MailService;
import com.baemin.service.UserService;
import com.baemin.util.UserInfoSessionUpdate;
import com.baemin.vo.Point;
import com.baemin.vo.Review;
import com.baemin.vo.User;
import com.nimbusds.oauth2.sdk.Request;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Controller
public class UserController {

	private static final Logger LOGGER = LogManager.getLogger(UserController.class);
	
	@Autowired
	private BCryptPasswordEncoder encodePwd;

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Autowired
	private MailService mailService;
	
	@GetMapping("/myPage")
	public String myPage(@AuthenticationPrincipal LoginDetail user, Model model, HttpSession session, @AuthenticationPrincipal OAuth2User oauth) {

		
//		System.out.println(SecurityContextHolder.getContext());
//		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());;
		
		if (user != null) {
			
			System.out.println("유저 정보");
			System.out.println(user.getUser());
			
			System.out.println();
			System.out.println("user.getUser().getNickname() = " + user.getUser().getNickname());
			model.addAttribute("nickname", user.getUser().getNickname());
		}

		return "user/myPage";
	}

	@GetMapping("/login")
	public String login(HttpServletRequest request, HttpSession session) {
		String referer = (String) request.getHeader("referer");
		session.setAttribute("referer", referer);
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
	public int overlapCheck(String value, String valueType) {
//		value = 중복체크할 값
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
		if (user.getUser().getId() == id) {
			userService.deleteReview(orderNum);
		}
	}

	@GetMapping("/user/point")
	public String myPoint(@AuthenticationPrincipal LoginDetail user, Model model,HttpServletRequest request) {

		if (user != null) {
			long id = user.getUser().getId();
			List<Point> myPoint = userService.myPoint(id);
			model.addAttribute("myPoint", myPoint);
			model.addAttribute("point", user.getUser().getPoint());
		}
		
		System.out.println(request.getAttribute("count"));

		return "user/point";
	}

	@GetMapping("/user/myInfo")
	public String myInfo(@AuthenticationPrincipal LoginDetail user, Model model ) {
		String username= user.getUser().getUsername();
		String email = user.getUser().getEmail();
		
		model.addAttribute("username" , username);
		model.addAttribute("email", email);
		
		
		return "user/myInfo";
	}
	
	
	
	@ResponseBody
	@PatchMapping("/user/infoModify")
	public String infoModify(String value, String valueType, String password, @AuthenticationPrincipal LoginDetail user, HttpSession session) {
//		value = 변경할값
//		valueType = nickname, password, phone
		
		if(valueType.equals("password")) {
			String presentPassword = user.getUser().getPassword();
			if(!encodePwd.matches(password, presentPassword)) {
				return "현재 비밀번호가 일치하지 않습니다";
			}
			value = encodePwd.encode(value);
 		}
		
		long id = user.getUser().getId();
		userService.infoModify(value, valueType, id);
		UserInfoSessionUpdate.sessionUpdate(value, valueType, user, session);
		
		return "변경되었습니다";
	}
	
	
	
	@ResponseBody
	@GetMapping("/oauth")
	public String oauthLogin(@AuthenticationPrincipal LoginDetail user) {
		
		System.out.println(user);
		
		
		return"";
	}
	
	@GetMapping("/findId")
	public String findId() throws ServletException, IOException {
		return "user/findId";
	}
	
	@PostMapping("/findId")
	public String findId(String email, RedirectAttributes rttr) throws ServletException, IOException {
		
		List<String> usernames = userService.findId(email);
		
		System.out.println(email);
		
		if(usernames.size() != 0) {
			mailService.sendMail(email, usernames);
		}
		rttr.addFlashAttribute("sendEmailMessage", email);
			
		return "redirect:/findId";
	}
	

}
