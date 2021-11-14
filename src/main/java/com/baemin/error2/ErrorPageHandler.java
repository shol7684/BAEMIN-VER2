package com.baemin.error2;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
public class ErrorPageHandler {
	
	
	
	
	@ResponseBody
	@ExceptionHandler(NotFoundException.class)
	public String err(Exception e) {
		System.out.println(e.getMessage());
		return e.getMessage();
	}
	
	
//	@ResponseBody
//	@ExceptionHandler(IndexOutOfBoundsException.class)
//	public String er(Exception e) {
//		
//		System.out.println("에러");
//		return "aa";
//	}
	
	
	
//	@ResponseBody
//	@ExceptionHandler(NotFoundPage.class)
//	public String err(Exception e) {
//		
//		System.out.println("에러");
//		return "aa";
//	}
	
	
	
}
