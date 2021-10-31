package com.baemin.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.baemin.vo.User;

@Service
public class WebSocketHandler extends TextWebSocketHandler {

//	List<WebSocketSession> list = new ArrayList<>(); 
	
	List<WebSocketSession> admins = new ArrayList<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("연결됨 = " + session);
//		list.add(session);
		if(getId(session) != null) {
			admins.add(session);
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("handleTextMessage 세션= " + session);
		System.out.println("handleTextMessage 메세지= " + message);
		
		System.out.println("접속자 수 = "  + admins.size() );
		
		
		for(int i=0;i<admins.size();i++) {
			admins.get(i).sendMessage(new TextMessage(message.getPayload()));
		}
		
		String msg = message.getPayload();
		String userId = getId(session);
		
		System.out.println("userId = " + userId);
		
		System.out.println("msg = " + msg);
		
		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("연결 종료 = " + session);
		admins.remove(session);
	}
	
	
	private String getId(WebSocketSession seesion) {
		Map<String, Object> map = seesion.getAttributes();
		System.out.println("map = " + map);
		
		User user = (User) map.get("admin");
		
		if(user != null ) {
			return user.getNickname();
		}
		
		return null;
	}
	
	
	

}
