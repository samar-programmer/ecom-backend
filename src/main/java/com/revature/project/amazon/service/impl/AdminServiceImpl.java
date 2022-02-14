package com.revature.project.amazon.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project.amazon.model.Product;
import com.revature.project.amazon.model.ProductVarients;
import com.revature.project.amazon.model.VarientValues;
import com.revature.project.amazon.repository.ProductRepository;
import com.revature.project.amazon.repository.VarientRepository;
import com.revature.project.amazon.repository.VarientValueRepository;
import com.revature.project.amazon.service.AdminService;
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	VarientRepository varientRepository;
	 
	@Autowired
	VarientValueRepository varientValueRepository;
	
	@Override
	public void addProduct( Product product) {
		productRepository.saveAndFlush(product);
		
	}

	@Override
	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	@Override
	public void deleteProductbyId(Long productId) {
		
		productRepository.deleteById(productId);
		
	}

	@Override
	public Product getProductbyId(Long productId) {
		Optional<Product> product = productRepository.findById(productId);
		return product.get();
	}

	@Override
	public void saveVarientsForProduct(ProductVarients varients) {
		varientRepository.save(varients);
		
	}

	@Override
	public List<ProductVarients> getVarients() {
		return varientRepository.findAll();
	}

	@Override
	public List<ProductVarients> getvarientByProductId(long parseLong) {
		return  varientRepository.findByProductId((int)parseLong);
	}

	@Override
	public void deleteVarientbyId(Long varientId) {
		varientRepository.deleteById(varientId);
		
	}

	@Override
	public void deleteproductVarientById(int productId) {
	varientRepository.deleteByProductId((productId));
		
	}

	@Override
	public List<VarientValues> getvarientValuesByVarientId(long varientId) {
		return varientValueRepository.findByVarientId(varientId);
	}

	@Override
	public ProductVarients getvarientValuesbyVarientId(long parseLong) {
		
		Optional<ProductVarients> productVarients = varientRepository.findById(parseLong);
		return productVarients.get();
	}

	@Override
	public void addVarientValues(ProductVarients productVarients) {
		varientRepository.saveAndFlush(productVarients);
		
	}

	@Override
	public List<VarientValues> getVarientValues() {
		// TODO Auto-generated method stub
		return varientValueRepository.findAll();
	}

	@Override
	public void deleteVarientValuebyId(Long varientValueId) {
		varientValueRepository.deleteById(varientValueId);
		
	}

	@Override
	public void deleteVarientValueByVarientId(Long varientId) {
		varientValueRepository.deleteByVarientId(varientId);
		
	}

//	@Override
//	public List<ProductVarients> getVarientsByProductId() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<Product> getProducts(String email) {
		return productRepository.findAllByEmail(email);
	}

	@Override
	public List<ProductVarients> getVarients(String email) {
		return varientRepository.findAllByEmail(email);
	}

	@Override
	public List<VarientValues> getVarientValues(String email) {
		return varientValueRepository.findAllByEmail(email);
	}
	
	

}
