package com.revature.project.amazon.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ProductDto {

	
	private Long productId;
	private String category;
	private String productName;
	private String brand;
	private String model;
	private String imgsrc;
	private String quantity;
	private String productPrice;
	private String productDiscountPrice;
	private String addeddate;
	private String description;
	private String email;
	
}
