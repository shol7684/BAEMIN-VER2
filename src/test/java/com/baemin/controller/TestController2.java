package com.baemin.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController2 {

	@GetMapping("/")
	String main() {
		
		System.out.println("test");
		return null;
	}	
	
}
