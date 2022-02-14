package com.revature.project.amazon.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project.amazon.model.Cart;
import com.revature.project.amazon.model.Product;
import com.revature.project.amazon.repository.CartRepository;
import com.revature.project.amazon.repository.ProductRepository;
import com.revature.project.amazon.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Override
	public Product findByProductid(int parseInt) {
		
		Optional<Product> product = productRepository.findById((long) parseInt);
		
		return product.get();
	}

	@Override
	public void addProductToCart(Cart buf) {
		cartRepository.save(buf);
		
	}

	@Override
	public List<Cart> getCartList(String email) {
		return cartRepository.findByEmail(email);
	}

	@Override
	public Cart findByBufcartIdAndEmail(long cartId, String email) {
		return cartRepository.findByBufcartIdAndEmail(cartId,email);
	}

	@Override
	public List<Cart> findCartByEmail(String email) {
		
		return cartRepository.findByEmail(email);
	}

	@Override
	public void deleteBufcartIdAndEmail(long cartId, String email) {
		 cartRepository.deleteByBufcartIdAndEmail(cartId,email);
	}

}
