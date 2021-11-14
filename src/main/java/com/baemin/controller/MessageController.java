package com.baemin.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
	
	
	@MessageMapping("/order-complete-message")
	@SendTo("/topic/order-complete")
	public String message(String message) {
		
		System.out.println("메세지 도착 :" + message);
		
		
		
		return message;
	}

}
