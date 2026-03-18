package com.centrum.util;

public class validationUtils {
	
	
private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

public static String validateRegistrationInput(String fullname, String email, String password, boolean agreeTerms) {
		
		if(fullname == null || fullname.trim().isEmpty()) {
			return "fullname cannot be empty";
		}
		
		if(fullname.trim().length()<4) {
			return "fullname must be at least 4 characters long";
		}
		
		if(email == null || email.trim().isEmpty()) {
			return "email cannot be empty";
			
			
		}
		if(password == null || password.trim().isEmpty() || password.length() < 0){
			return "password cannot be empty";

		}
		if(password.length() < 8) {
			return "Password must be at least 8 characters";
		}
		
		if(!email.matches(EMAIL_REGEX)) {
			return "incorrect email address";

		}
		
		if(!agreeTerms ) {
			return "you must agree to the terms and conditions";
		}
		return null;
		
	}


}
