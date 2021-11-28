package com.baemin.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderDetail {

	private String orderNum;
	private String foodInfoJSON;
}
