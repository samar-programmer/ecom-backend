package com.revature.project.amazon.response;

import java.io.ByteArrayInputStream;

import com.revature.project.amazon.model.User;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ServerResponse {
	private String status;
	private String message;
	private String userType;
	private String successErrorType;
	private User user;
	private ByteArrayInputStream pdfg;
}
