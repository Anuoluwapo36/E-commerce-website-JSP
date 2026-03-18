package com.centrum.bean;

import java.sql.Timestamp;

public class Logout {

	private int logout_id;
	private int user_id;
	private Timestamp logoutTimestamp;
	private String ipAddress;
	private String userAgent;

	public int getLogout_id() {
		return logout_id;
	}

	public void setLogout_id(int logout_id) {
		this.logout_id = logout_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Timestamp getLogoutTimestamp() {
		return logoutTimestamp;
	}

	public void setLogoutTimestamp(Timestamp logoutTimestamp) {
		this.logoutTimestamp = logoutTimestamp;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

}
