package com.baemin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendMail(List<String> usernames) {
		
		String to = "shol7684@naver.com";
		
		SimpleMailMessage simpleMailMessage = new  SimpleMailMessage();
		
		simpleMailMessage.setTo(to);
		
		simpleMailMessage.setSubject("아이디 찾기");
		
		simpleMailMessage.setText("가입하신 아이디는 " + usernames + "입니디");
		
		mailSender.send(simpleMailMessage);
		
	}

}
