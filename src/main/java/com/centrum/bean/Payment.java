package com.centrum.bean;

import java.sql.Timestamp;

public class Payment {
	private int payment_id;
	private int user_id;
	private int order_id;
	private int address_id;
	private String payment_status;
	private int payment_method;
	private double amount;
	private Timestamp payment_date;

	private String card_last4;
	private String card_expiry;
	private String cardholder_name;

	public String getCard_last4() {
		return card_last4;
	}

	public void setCard_last4(String card_last4) {
		this.card_last4 = card_last4;
	}

	public String getCard_expiry() {
		return card_expiry;
	}

	public void setCard_expiry(String card_expiry) {
		this.card_expiry = card_expiry;
	}

	public String getCardholder_name() {
		return cardholder_name;
	}

	public void setCardholder_name(String cardholder_name) {
		this.cardholder_name = cardholder_name;
	}

	public Payment() {
		this.payment_date = new Timestamp(System.currentTimeMillis());
	}

	public int getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public String getPayment_status() {
		return payment_status;
	}

	public void setPayment_status(String payment_status) {
		this.payment_status = payment_status;
	}

	public int getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(int payment_method) {
		this.payment_method = payment_method;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(Timestamp payment_date) {
		this.payment_date = payment_date;
	}

}
