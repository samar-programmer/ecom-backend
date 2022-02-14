package com.revature.project.amazon.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project.amazon.constants.ResponseCode;
import com.revature.project.amazon.exception.CartCustomException;
import com.revature.project.amazon.model.Cart;
import com.revature.project.amazon.model.Product;
import com.revature.project.amazon.response.CartResponse;
import com.revature.project.amazon.response.ServerResponse;
import com.revature.project.amazon.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
@RestController
public class UesrController {

	@Autowired
	UserService userService;

	@GetMapping("/addToCart")
	public ResponseEntity<ServerResponse> addToCart(@RequestParam("productId") String productId,
			@RequestParam("email") String email, @RequestParam("total") String total) {

		ServerResponse serverResponse = new ServerResponse();
		try {

			Product cartItem = userService.findByProductid(Integer.parseInt(productId));

			Cart buf = new Cart();
			buf.setEmail(email);
			buf.setQuantity(1);
			buf.setPrice(Double.parseDouble(cartItem.getProductPrice()));
			buf.setProductId(Integer.parseInt(productId));
			buf.setProductname(cartItem.getProductName());
			buf.setTotal(total);
			Date date = new Date();
			buf.setDateAdded(date);

			userService.addProductToCart(buf);

			serverResponse.setStatus(ResponseCode.SUCCESS_CODE);
			serverResponse.setMessage(ResponseCode.CART_UPD_MESSAGE_CODE);
		} catch (Exception e) {
			throw new CartCustomException("Unable to add product to cart, please try again");
		}
		return new ResponseEntity<ServerResponse>(serverResponse, HttpStatus.OK);
	}

	@GetMapping("/viewCart")
	public ResponseEntity<CartResponse> viewCart(@RequestParam("email") String email) {
		CartResponse resp = new CartResponse();
		try {
			resp.setStatus(ResponseCode.SUCCESS_CODE);
			resp.setMessage(ResponseCode.VW_CART_MESSAGE);
			resp.setOblist(userService.getCartList(email));
		} catch (Exception e) {
			throw new CartCustomException("Unable to retrieve cart items, please try again");
		}

		return new ResponseEntity<CartResponse>(resp, HttpStatus.OK);
	}

	@PutMapping("/updateCart")
	public ResponseEntity<CartResponse> updateCart(@RequestBody HashMap<String, String> cart) {

		CartResponse resp = new CartResponse();
		try {

			Cart selCart = userService.findByBufcartIdAndEmail(Long.parseLong(cart.get("id")),
					cart.get("email").toString());
			selCart.setQuantity(Integer.parseInt(cart.get("quantity")));
			userService.addProductToCart(selCart);
			List<Cart> bufcartlist = userService.findCartByEmail(cart.get("email").toString());
			resp.setStatus(ResponseCode.SUCCESS_CODE);
			resp.setMessage(ResponseCode.UPD_CART_MESSAGE);
			resp.setOblist(bufcartlist);
		} catch (Exception e) {
			throw new CartCustomException("Unable to update cart items, please try again");
		}

		return new ResponseEntity<CartResponse>(resp, HttpStatus.OK);
	}

	@DeleteMapping("/delCart")
	public ResponseEntity<CartResponse> delCart(@RequestParam("bufcartid") String bufcartid,
			@RequestParam("email") String email) {

		CartResponse resp = new CartResponse();
		try {

			userService.deleteBufcartIdAndEmail(Long.parseLong(bufcartid), email);
			List<Cart> bufcartlist = userService.findCartByEmail(email);
			resp.setStatus(ResponseCode.SUCCESS_CODE);
			resp.setMessage(ResponseCode.DEL_CART_SUCCESS_MESSAGE);
			resp.setOblist(bufcartlist);
		} catch (Exception e) {
			throw new CartCustomException("Unable to delete cart items, please try again");
		}
		return new ResponseEntity<CartResponse>(resp, HttpStatus.OK);
	}

}
