package com.ecasona.newapi.pay.model;

public class OrderInfo {

	private String orderInfo;

	public OrderInfo(String content) {
		orderInfo=content;
	}
	
	public String getContent(){
		return orderInfo;
	}
}
