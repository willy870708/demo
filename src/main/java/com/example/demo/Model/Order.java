package com.example.demo.Model;

import lombok.Data;

@Data
public class Order {
	private String orderId;
	private String hosterId;
	private String orderDate;
	private String endDate;
	private String orderType;
	private String storeId;
	private String contactMethod;
	private String amountLimit;
	private String status;
}
