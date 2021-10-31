package com.baemin.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {

	private long id;
	private String username;
	private String password;
	private String email;
	private String nickname;
	private long point;
	private String phone;
	private String rating;
	private String role;
	
//	public User(String username, String password, String email, String nickname, String phone) {
//		this.username = username;
//		this.password = password;
//		this.email = email;
//		this.nickname = nickname;
//		this.phone = phone;
//	}
	
	

}
