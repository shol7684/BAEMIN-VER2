package com.baemin.vo;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Point {

	private long userId;
	private Timestamp usedDate;
	private String info;
	private int point;
}
