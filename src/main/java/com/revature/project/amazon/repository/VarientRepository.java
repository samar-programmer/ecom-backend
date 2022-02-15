package com.revature.project.amazon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.project.amazon.model.ProductVarient;
@Repository
public interface VarientRepository extends JpaRepository<ProductVarient, Long> {
	
	List<ProductVarient> findByProductId(int parseLong);
	
	void deleteByProductId( int productId);

	List<ProductVarient> findAllByEmail(String email);

}
