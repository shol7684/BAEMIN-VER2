package com.baemin.vo;

import lombok.Getter;
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

}
