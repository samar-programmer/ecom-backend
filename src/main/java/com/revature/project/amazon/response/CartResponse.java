package com.revature.project.amazon.response;

import java.util.List;

import com.revature.project.amazon.model.Cart;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class CartResponse {
	private String status;
	private String message;
	private String AUTH_TOKEN;
	private List<Cart> oblist;
	
}