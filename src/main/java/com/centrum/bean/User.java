/**
 * 
 */
package com.centrum.bean;

import java.sql.Timestamp;

/**
 * 
 */
public class User {
	
	private int user_id;
	private String full_name;
	private String email;
	private String phone_number;
	private String password;
	private String roles;
	private String status;
	private Timestamp createAt;
	
	
	public User() {}


	public User(int user_id, String full_name, String email, String phone_number, String password, String roles, String status,
			Timestamp createAt) {
		super();
		this.user_id = user_id;
		this.full_name = full_name;
		this.email = email;
		this.phone_number = phone_number;
		this.password = password;
		this.roles = roles;
		this.status = status;
		this.createAt = createAt;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public String getFull_name() {
		return full_name;
	}


	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone_number() {
		return phone_number;
	}


	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRoles() {
		return roles;
	}


	public void setRoles(String roles) {
		this.roles = roles;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Timestamp getCreateAt() {
		return createAt;
	}


	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
	


	
	
}
