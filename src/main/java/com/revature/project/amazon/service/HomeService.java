package com.revature.project.amazon.service;

import com.revature.project.amazon.model.User;

public interface HomeService {

	void signupUser(User user);

	User verifyUser(String email, String password);

	boolean checkUserExists(String email);

	boolean sendEmail(String subject, String message, String to);

	int updateUserByOtp(String to, long otp);

	User fetchByOtp(long tempOtp);

	boolean sendPassword(String subject, String tempPassword, String tempEmail);

	User getProfile(String email);

}
