package com.revature.project.amazon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.project.amazon.model.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	List<Cart> findByEmail(String email);

	Cart findByBufcartIdAndEmail(long cartId, String email);

	void deleteByBufcartIdAndEmail(long cartId, String email);

}
