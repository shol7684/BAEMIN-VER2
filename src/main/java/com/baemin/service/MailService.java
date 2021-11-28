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
	
	public void sendUsernames(String eamil, List<String> usernames) {
		SimpleMailMessage simpleMailMessage = new  SimpleMailMessage();
		simpleMailMessage.setTo(eamil);
		simpleMailMessage.setSubject("아이디 찾기");
		
		StringBuffer sb = new StringBuffer();
		sb.append("가입하신 아이디는");
		sb.append(System.lineSeparator());
		
		for(int i=0;i<usernames.size()-1;i++) {
			sb.append(usernames.get(i));
			sb.append(System.lineSeparator());
		}
		sb.append(usernames.get(usernames.size()-1)).append("입니다");
		
		simpleMailMessage.setText(sb.toString());
		
		mailSender.send(simpleMailMessage);
	}
	
	
	public void sendAuthNum(String email, String authNum) {
		SimpleMailMessage simpleMailMessage = new  SimpleMailMessage();
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject("인증번호 발송");
		
		StringBuffer sb = new StringBuffer();
		sb.append("인증번호는 ");
		sb.append(authNum);
		sb.append(" 입니다");
		
		simpleMailMessage.setText(sb.toString());
		mailSender.send(simpleMailMessage);
	}
	

}
