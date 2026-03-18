package com.centrum.util;

public class ValidateSettings {

	public static String validateSettingsInput(String fullName, String email, String password) {
		if (fullName == null || fullName.trim().isEmpty()) {
			return "Full name cannot be empty";
		}
		if (fullName.trim().length() < 2) {
			return "Full name must be at least 2 characters";
		}

		if (email == null || email.trim().isEmpty()) {
			return "Email cannot be empty";
		}
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
		if (!email.trim().matches(emailRegex)) {
			return "Invalid email format (e.g., example@domain.com)";
		}

		if (password != null && !password.trim().isEmpty()) {
			if (password.length() < 6) {
				return "Password must be at least 6 characters";
			}
		}

		// No errors → validation passed
		return null;
	}
}
