package com.revature.project.amazon.response;

import java.util.List;

import com.revature.project.amazon.model.Product;
import com.revature.project.amazon.model.ProductVarient;
import com.revature.project.amazon.model.VarientValue;
import com.revature.project.amazon.utility.AllProductInfoDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ProductResponse {
	private String status;
	private String message;
	private String successErrorType;
	private List<Product> productList;
	private List<ProductVarient> varientList;
	private List<VarientValue> varientValuesList;
	
	private List<AllProductInfoDTO> allProductInfoDTO;
}
