package com.revature.project.amazon.controller;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project.amazon.constants.ResponseCode;
import com.revature.project.amazon.exception.UserCustomException;
import com.revature.project.amazon.model.User;
import com.revature.project.amazon.response.ServerResponse;
import com.revature.project.amazon.service.HomeService;
import com.revature.project.amazon.utility.PDFGenerator;
import com.revature.project.amazon.utility.Validator;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/home")
public class HomeController {

	@Autowired
	private HomeService homeService;
	
	@Autowired
	PDFGenerator pdfgenerator;

	@PostMapping("/signup")
	public ResponseEntity<ServerResponse> addUser(@RequestBody User user) {

		ServerResponse serverResponse = new ServerResponse();
		try {

			boolean userExists = homeService.checkUserExists(user.getEmail());

			if (userExists == true) {
				serverResponse.setStatus(ResponseCode.ALREADY_EXISTS_CODE);
				serverResponse.setMessage(ResponseCode.ALREADY_EXISTS_MESSAGE);
				serverResponse.setSuccessErrorType("ERROR");
			} else {
				if (Validator.isUserEmpty(user)) {
					serverResponse.setStatus(ResponseCode.BAD_REQUEST_CODE);
					serverResponse.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
				} else if (!Validator.isValidEmail(user.getEmail())) {
					serverResponse.setStatus(ResponseCode.BAD_REQUEST_CODE);
					serverResponse.setMessage(ResponseCode.INVALID_EMAIL_FAIL_MSG);
				} else {
					System.out.println(user);
					homeService.signupUser(user);
					serverResponse.setStatus(ResponseCode.SUCCESS_CODE);
					serverResponse.setMessage(ResponseCode.CUST_REG);
					serverResponse.setSuccessErrorType(ResponseCode.SUCCESS);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();

			throw new UserCustomException("An error occured while saving user, please check details or try again");
		}
		return new ResponseEntity<ServerResponse>(serverResponse, HttpStatus.ACCEPTED);
	}

	@GetMapping("/login")
	public ResponseEntity<ServerResponse> verifyUser(@RequestParam String email, @RequestParam String password) {

		ServerResponse serverResponse = new ServerResponse();
		try {
			Optional<User> user = Optional.ofNullable(homeService.verifyUser(email, password));// ofNullable - return
																								// value if present
																								// otherwise retun empty
																								// optional object // of
																								// - if we pass null
																								// value it throws
																								// exception
			String sasi = user.get().getEmail();
			if (user.isPresent()) {
				if (user.get().getPassword().equalsIgnoreCase(password)) {
					serverResponse.setMessage(ResponseCode.SUCCESS_LOGIN);
					serverResponse.setStatus(ResponseCode.SUCCESS_CODE);
					serverResponse.setUserType(user.get().getRole());
					serverResponse.setSuccessErrorType("SUCCESS");
				} else {
					serverResponse.setMessage(ResponseCode.INVALID_LOGIN);
					serverResponse.setStatus(ResponseCode.UNAUTHORIZED_CODE);
				}
			} else {
				serverResponse.setMessage(ResponseCode.FAILURE_LOGIN);
				serverResponse.setStatus(ResponseCode.FAILURE_CODE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new UserCustomException("An error occured while login, please check credentials or try again");
		}
		return new ResponseEntity<ServerResponse>(serverResponse, HttpStatus.OK);
	}

	@PostMapping("/send-otp")
	public String sendOTP(@RequestBody User ecommerceuser) {
		Random random = new Random(1000);
		long otp = random.nextInt(999999);

		String subject = "OTP from AMAZON";
		String message = "OTP to Change Your Password is = " + otp;
		System.out.println("Hello otp");
		System.out.println(ecommerceuser.getEmail());
		String to = ecommerceuser.getEmail();

		System.out.println(message);
		System.out.println(to);

		boolean flag = this.homeService.sendEmail(subject, message, to);

		System.out.println(to);
		int i = homeService.updateUserByOtp(to, otp);

		if (flag) {
			return "Verify";
		} else {
			return "Wrong Credentials";
		}
	}

	
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestBody User ecommerceuser) {
		long tempOtp = ecommerceuser.getOtp();

		if (tempOtp != 0) {
			ecommerceuser = homeService.fetchByOtp(tempOtp);

		}
		String tempEmail = ecommerceuser.getEmail();
		String tempPassword = ecommerceuser.getPassword();
		String subject = "PASSWORD From Amazon";

		boolean flag = this.homeService.sendPassword(subject, tempPassword, tempEmail);

		if (ecommerceuser != null) {
			return "Success";
		} else {
			return "Incorrect OTP";
		}

	}
	
	
	@PostMapping("/getprofile")
	public ResponseEntity<ServerResponse> getProfile(@RequestParam String email) {
		User user ;
		ServerResponse serverResponse = new ServerResponse();
		try {

			 user = homeService.getProfile(email);
			 serverResponse.setUser(user);

		} catch (Exception e) {
			e.printStackTrace();

			throw new UserCustomException("An error occured while get profile");
		}
		return new ResponseEntity<ServerResponse>(serverResponse, HttpStatus.ACCEPTED);
	}
	
	
	
	
	@PostMapping("/editprofile")
	public ResponseEntity<ServerResponse> editUser(@RequestBody User user) {

		ServerResponse serverResponse = new ServerResponse();
		try {

					homeService.signupUser(user);
					serverResponse.setStatus(ResponseCode.SUCCESS_CODE);
					serverResponse.setMessage(ResponseCode.CUST_REG);
					serverResponse.setSuccessErrorType(ResponseCode.SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();

			throw new UserCustomException("An error occured while editing user, please check details or try again");
		}
		return new ResponseEntity<ServerResponse>(serverResponse, HttpStatus.ACCEPTED);
	}
	 
	@GetMapping("/generatePdf")
	public ResponseEntity<InputStreamResource> getReportUser(@RequestParam("email") String email) {

		ServerResponse serverResponse = new ServerResponse();

		ByteArrayInputStream bis = pdfgenerator.generatePdfReport(email);
		serverResponse.setMessage("success");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=cart.pdf");

		return ResponseEntity.ok().headers	(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));

	}

	

}
