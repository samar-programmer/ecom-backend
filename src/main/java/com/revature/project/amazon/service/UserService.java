package com.revature.project.amazon.service;

import java.util.List;

import com.revature.project.amazon.model.Cart;
import com.revature.project.amazon.model.Product;

public interface UserService {

	Product findByProductid(int parseInt);

	void addProductToCart(Cart buf);

	List<Cart> getCartList(String email);

	Cart findByBufcartIdAndEmail(long cartId, String email);

	List<Cart> findCartByEmail(String string);

	void deleteBufcartIdAndEmail(long cartId, String email);

}
