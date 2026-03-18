package com.centrum.bean;

public class User_address {
	private int address_id;
	private int user_id;
	private String country;
	private String state;
	private String city;
	private String details_address;
	private String postal_code;
	private String phone_number;
	private boolean default_address;

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDetails_address() {
		return details_address;
	}

	public void setDetails_address(String details_address) {
		this.details_address = details_address;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public boolean isDefault_address() {
		return default_address;
	}

	public void setDefault_address(boolean default_address) {
		this.default_address = default_address;
	}

}
