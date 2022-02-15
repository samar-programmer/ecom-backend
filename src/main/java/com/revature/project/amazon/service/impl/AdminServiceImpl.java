package com.revature.project.amazon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project.amazon.model.Product;
import com.revature.project.amazon.model.ProductVarient;
import com.revature.project.amazon.model.VarientValue;
import com.revature.project.amazon.repository.ProductRepository;
import com.revature.project.amazon.repository.VarientRepository;
import com.revature.project.amazon.repository.VarientValueRepository;
import com.revature.project.amazon.response.ProductResponse;
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

	int totalcheck = 0;
	String radiocheck = "yes";

	@Override
	public void addProduct(Product product) {
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
	public void saveVarientsForProduct(ProductVarient varients) {
		varientRepository.save(varients);

	}

	@Override
	public List<ProductVarient> getVarients() {
		return varientRepository.findAll();
	}

	@Override
	public List<ProductVarient> getvarientByProductId(long parseLong) {
		return varientRepository.findByProductId((int) parseLong);
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
	public List<VarientValue> getvarientValuesByVarientId(long varientId) {
		return varientValueRepository.findByVarientId(varientId);
	}

	@Override
	public ProductVarient getvarientValuesbyVarientId(long parseLong) {

		Optional<ProductVarient> productVarients = varientRepository.findById(parseLong);
		return productVarients.get();
	}

	@Override
	public void addVarientValues(ProductVarient productVarients) {
		varientRepository.saveAndFlush(productVarients);

	}

	@Override
	public List<VarientValue> getVarientValues() {
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

	@Override
	public List<Product> getProducts(String email) {
		return productRepository.findAllByEmail(email);
	}

	@Override
	public List<ProductVarient> getVarients(String email) {
		return varientRepository.findAllByEmail(email);
	}

	@Override
	public List<VarientValue> getVarientValues(String email) {
		return varientValueRepository.findAllByEmail(email);
	}

	@Override
	public List<Product> getOriginalProductDrtails(ProductResponse productResponse) {

		List<Product> originalProductData = new ArrayList();

		productResponse.getProductList().stream().forEach(product -> {

			String totalPrice = product.getProductDiscountPrice();
			radiocheck = "yes";
			product.setTotalPrice(totalPrice);

			List<ProductVarient> productVarients = getvarientByProductId(product.getProductId());

			productVarients.stream().filter(varentvalue -> totalcheck == 0).forEach(varentvalue -> {
				List<VarientValue> list = getvarientValuesByVarientId(varentvalue.getVarientId());
				list.stream().filter(values -> radiocheck.equalsIgnoreCase("yes")).forEach(values -> {
					int FirstProductValue = Integer.parseInt(totalPrice) + Integer.parseInt(values.getPrice());
					product.setTotalPrice(String.valueOf(FirstProductValue));
					product.setRadioCheck("yes");
					radiocheck = "no";
					totalcheck++;
				});
				totalcheck = 0;
				varentvalue.setVarientvalues(list);
			});

			product.setVarients(productVarients);
			originalProductData.add(product);

		});

		return originalProductData;
	}

}
