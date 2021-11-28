package com.baemin.controller;

import java.io.File;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.*;

import org.assertj.core.util.Arrays;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.baemin.vo.Cart;
import com.baemin.vo.CartDetail;
import com.google.gson.Gson;

class JunitTest {

	
	@Test
	void list() throws ParseException {
		System.out.println(123);
		
		String ss  = "{\"foodId\":1,\"foodName\":\"불고기피자\",\"foodPrice\":12000,\"amount\":1,\"totalPrice\":12000}\r\n"+ "/{\"foodId\":1,\"foodName\":\"불고기피자\",\"foodPrice\":12000,\"amount\":1,\"totalPrice\":14000,\"optionName\":[\"베이컨 토핑 추가\",\"치즈 토핑 추가\"],\"optionPrice\":[1000,1000],\"optionId\":[3,5]}";
		
		System.out.println(ss);
		
		String[] arr = ss.split("/");
		
		Gson gson = new Gson();
		
		for(int i=0;i<arr.length;i++) {
			Cart cart = gson.fromJson(arr[i], Cart.class);
			System.out.println(cart);
		}
		
		
		
	}
	
	
	         
//	@Test
	void upload() {
		String path = "C:\\resource";
		
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		
		System.out.println("yearPath = " + yearPath);
		System.out.println("monthPath = " + monthPath);
		System.out.println("datePath = " + datePath);
		System.out.println(path+yearPath);
		System.out.println(path+monthPath);
		System.out.println(path+datePath);
		
		System.out.println(new File(path, datePath).exists());
		
		if(new File(path, datePath).exists()) {
			return;
		}
		UUID uuid = UUID.randomUUID();
		
		new File(path, yearPath).mkdir();
		new File(path, monthPath).mkdir();
		new File(path, datePath).mkdir();
		
		
		System.out.println(uuid);
		System.out.println(1);
		
		
		
	}	
	
	
	
	
	
//	@Test
	void gson() throws JSONException, ParseException {
		Gson gson = new Gson();

		String s2 = "{\"code\":0,\"message\":null,\"response\":{\"amount\":1000.3,\"apply_num\":\"63834825\",\"bank_code\":null,\"bank_name\":null,\"buyer_addr\":\"\\ucda9\\ub0a8 \\ucc9c\\uc548\\uc2dc \\uc11c\\ubd81\\uad6c \\ub178\\ud0dc\\uc0b0\\ub85c 4 \",\"buyer_email\":null,\"buyer_name\":null,\"buyer_postcode\":\"31099\",\"buyer_tel\":\"01028857684\",\"cancel_amount\":0,\"cancel_history\":[],\"cancel_reason\":null,\"cancel_receipt_urls\":[],\"cancelled_at\":0,\"card_code\":\"371\",\"card_name\":\"NH\\uce74\\ub4dc\",\"card_number\":\"542416*********4\",\"card_quota\":0,\"card_type\":0,\"cash_receipt_issued\":false,\"channel\":\"pc\",\"currency\":\"KRW\",\"custom_data\":null,\"customer_uid\":null,\"customer_uid_usage\":null,\"emb_pg_provider\":\"naverpay\",\"escrow\":false,\"fail_reason\":null,\"failed_at\":0,\"imp_uid\":\"imp_845238349117\",\"merchant_uid\":\"20211180129193085\",\"name\":\"\\uace0\\uad6c\\ub9c8\\ud53c\\uc790\",\"paid_at\":1636324303,\"pay_method\":\"card\",\"pg_id\":\"INIpayTest\",\"pg_provider\":\"html5_inicis\",\"pg_tid\":\"StdpayCARDINIpayTest20211108073142753076\",\"receipt_url\":\"https:\\/\\/iniweb.inicis.com\\/DefaultWebApp\\/mall\\/cr\\/cm\\/mCmReceipt_head.jsp?noTid=StdpayCARDINIpayTest20211108073142753076&noMethod=1\",\"started_at\":1636324238,\"status\":\"paid\",\"user_agent\":\"Mozilla\\/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit\\/537.36 (KHTML, like Gecko) Chrome\\/95.0.4638.69 Safari\\/537.36\",\"vbank_code\":null,\"vbank_date\":0,\"vbank_holder\":null,\"vbank_issued_at\":0,\"vbank_name\":null,\"vbank_num\":null}}";

		JSONObject json = new JSONObject();

		JSONParser parser = new JSONParser();

		JSONObject p = (JSONObject) parser.parse(s2);

//		 String response = p.get("response").toString();
//		 
//		 
//		 System.out.println("response = " + response);
//		 
//		 JSONObject amount = (JSONObject)parser.parse(response);
//		 
//		 System.out.println(amount.get("amount"));
//		 
//		 json.put("abc", "abc");
//		 System.out.println(json);

		String response = p.get("response").toString();

		System.out.println(response);

		p = (JSONObject) parser.parse(response);

		System.out.println(p.get("amount"));
	}

	//@Test
	void a() {
		String re = "http://localhost:8080/order";
		String domain = "http://localhost:8080";
		
		System.out.println(re);
		System.out.println(domain);
		
		System.out.println(re.replace(domain, ""));
	}

}
