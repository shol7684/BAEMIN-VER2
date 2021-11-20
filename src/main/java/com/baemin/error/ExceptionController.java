package com.baemin.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baemin.controller.AdminController;

@Controller
public class ExceptionController implements ErrorController{
	
	private static final Logger LOGGER = LogManager.getLogger(ExceptionController.class);
	
	@GetMapping("/error")
	public String error(HttpServletRequest request, Model model) {
		
		System.out.println("에러");
		
		String errorCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString();
		
		LOGGER.info("에러");
		LOGGER.info("errorCode = " + errorCode);
		
		model.addAttribute("errorCode", errorCode);
		
		return "error/errorPage";
	}

}
