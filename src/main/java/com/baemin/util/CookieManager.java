package com.baemin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashSet;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {

	
	public String findCookie(String cookieName, HttpServletRequest request) throws UnsupportedEncodingException {
		
		Cookie[] cookies = request.getCookies();
		String Cname = null;
		
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(cookieName)) {
				Cname = URLDecoder.decode(cookies[i].getValue(), "UTF-8");
				break;
			}
		}
		
		return Cname;
	}
	
	
	public LinkedHashSet<String> cookieSave(HttpServletResponse response, String ...name) throws UnsupportedEncodingException {
		
		if(name.length != 0) {
//			System.out.println("name[0] = " + name[0]);
//			if(name[0] == null) {
//				return  null;
//			}
			
			LinkedHashSet<String> set = new LinkedHashSet<>();
				
			for(int i=0;i<name.length-1;i++) {
				set.add(name[i]);
			}
				
			if(name[name.length-1] != null) {
				StringTokenizer st = new StringTokenizer(name[name.length-1], ", ");
				
				while (st.hasMoreTokens()) {
					set.add(st.nextToken());
					
					if (set.size() > 7) {
						break;
					}
				}
			}
			
			addCookie(set.toString(), "keyword", response);
			return set;
		}
		
		return null;
	}
	
	
	public void addCookie(String cookie, String saveName, HttpServletResponse response) throws UnsupportedEncodingException {
		
		if(cookie.startsWith("[")) {
			cookie =cookie.substring(1, cookie.length());
		}
		
		if(cookie.endsWith("]")) {
			cookie = cookie.substring(0, cookie.length()-1);
		}
		
		Cookie ck = new Cookie(saveName, URLEncoder.encode(cookie, "UTF-8"));
		ck.setMaxAge(60 * 60 * 24 * 30);
		response.addCookie(ck);
	}
	
	
	public LinkedHashSet<String> removeKeyword(HttpServletResponse response, String cookie, String keyword) throws UnsupportedEncodingException {
		
		LinkedHashSet<String> set = new LinkedHashSet<>();
			
		StringTokenizer st = new StringTokenizer(cookie, ", ");
		
		while (st.hasMoreTokens()) {
			set.add(st.nextToken());
			
			if (set.size() > 7) {
				break;
			}
		}
		
		set.remove(keyword);
		
		addCookie(set.toString(), "keyword", response);
		return set;
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
