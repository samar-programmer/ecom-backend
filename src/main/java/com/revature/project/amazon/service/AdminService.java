package com.revature.project.amazon.service;

import java.util.List;

import javax.validation.Valid;

import com.revature.project.amazon.model.Product;
import com.revature.project.amazon.model.ProductVarient;
import com.revature.project.amazon.model.VarientValue;
import com.revature.project.amazon.response.ProductResponse;

public interface AdminService {

	void addProduct(Product product);

	List<Product> getProducts();

	void deleteProductbyId(Long productId);

	Product getProductbyId(Long productId);

	void saveVarientsForProduct(ProductVarient varients);

	List<ProductVarient> getVarients();

	List<ProductVarient> getvarientByProductId(long parseLong);

	void deleteVarientbyId(Long productId);

	void deleteproductVarientById(int productId);

	List<VarientValue> getvarientValuesByVarientId(long parseLong);

	ProductVarient getvarientValuesbyVarientId(long parseLong);

	void addVarientValues(ProductVarient productVarients);

	List<VarientValue> getVarientValues();

	void deleteVarientValuebyId(Long varientValueId);

	void deleteVarientValueByVarientId(Long varientId);

	//List<ProductVarients> getVarientsByProductId();

	List<Product> getProducts(String email);

	List<ProductVarient> getVarients(String email);

	List<VarientValue> getVarientValues(String email);

	List<Product> getOriginalProductDrtails(ProductResponse productResponse);

	

}
