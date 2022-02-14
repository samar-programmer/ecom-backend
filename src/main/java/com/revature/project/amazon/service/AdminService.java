package com.revature.project.amazon.service;

import java.util.List;

import javax.validation.Valid;

import com.revature.project.amazon.model.Product;
import com.revature.project.amazon.model.ProductVarients;
import com.revature.project.amazon.model.VarientValues;

public interface AdminService {

	void addProduct(Product product);

	List<Product> getProducts();

	void deleteProductbyId(Long productId);

	Product getProductbyId(Long productId);

	void saveVarientsForProduct(ProductVarients varients);

	List<ProductVarients> getVarients();

	List<ProductVarients> getvarientByProductId(long parseLong);

	void deleteVarientbyId(Long productId);

	void deleteproductVarientById(int productId);

	List<VarientValues> getvarientValuesByVarientId(long parseLong);

	ProductVarients getvarientValuesbyVarientId(long parseLong);

	void addVarientValues(ProductVarients productVarients);

	List<VarientValues> getVarientValues();

	void deleteVarientValuebyId(Long varientValueId);

	void deleteVarientValueByVarientId(Long varientId);

	//List<ProductVarients> getVarientsByProductId();

	List<Product> getProducts(String email);

	List<ProductVarients> getVarients(String email);

	List<VarientValues> getVarientValues(String email);

	

}
