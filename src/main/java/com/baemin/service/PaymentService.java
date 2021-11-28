package com.baemin.service;

import java.io.IOException;

import org.json.simple.parser.ParseException;

public interface PaymentService {

	public String getToken() throws IOException; 
	public String paymentInfo(String imp_uid, String access_token) throws IOException, ParseException;
	public void payMentCancle(String access_token, String imp_uid, String amount, String reason) throws IOException, ParseException;
}
