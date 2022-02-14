package com.revature.project.amazon.utility;

import com.revature.project.amazon.model.User;

;

public class Validator {
	public static boolean isUserEmpty(User user) {
		if (user.getAge() == 0) {
			return true;
		}
		if (user.getPassword() == null || user.getPassword() == "") {
			return true;
		}
		if (user.getEmail() == null || user.getEmail() == "") {
			return true;
		}
		if (user.getFirstname() == null || user.getFirstname() == "") {
			return true;
		}
		if (user.getLastname() == null || user.getLastname() == "") {
			return true;
		}
		return false;
	}
	
	public static boolean isValidEmail(String input) {
		if (input != null && input != "") {
			if (input.matches("^[a-zA-Z0-9._]*@[a-zA-Z0-9.-]*$")) {
				return true;
			}
		}
		return false;
	}
}
