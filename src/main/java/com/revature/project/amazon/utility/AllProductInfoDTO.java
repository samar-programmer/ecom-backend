package com.revature.project.amazon.utility;

import java.util.List;

import com.revature.project.amazon.model.ProductVarients;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AllProductInfoDTO {
	private String category;
	private String productName;
	private String brand;//
	private String model;
	private String imgsrc;
	private String quantity;
	private String productPrice;
	private String productDiscountPrice;
	private String addeddate;
	private String description;
	private List<ProductVarients> productVarients;
}
