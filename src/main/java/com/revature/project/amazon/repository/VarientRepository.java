package com.revature.project.amazon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.project.amazon.model.ProductVarients;
@Repository
public interface VarientRepository extends JpaRepository<ProductVarients, Long> {
	
	List<ProductVarients> findByProductId(int parseLong);
	
	void deleteByProductId( int productId);

	List<ProductVarients> findAllByEmail(String email);

}
