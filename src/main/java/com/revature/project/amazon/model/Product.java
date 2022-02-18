package com.revature.project.amazon.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products_table")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;
	
	@NotBlank(message = "category is mandatory")
	private String category;
	@NotBlank(message = "productName is mandatory")
	private String productName;
	@NotBlank(message = "brand is mandatory")
	private String brand;
	@NotBlank(message = "model is mandatory")
	@Column(unique=true)
	private String model;
	@NotBlank(message = "imgsrc is mandatory")
	private String imgsrc;
	@NotBlank(message = "quantity is mandatory")
	private String quantity;
	@NotBlank(message = "productPrice is mandatory")
	private String productPrice;
	@NotBlank(message = "productDiscountPrice is mandatory")
	private String productDiscountPrice;
	@NotBlank(message = "addeddate is mandatory")
	private String addeddate;
	@NotBlank(message = "description is mandatory")
	private String description;
	
	private String totalPrice;
	private String radioCheck;
	private String email;
	
	
	//@OneToMany(targetEntity=ProductVarients.class, mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
	 @OneToMany(cascade = CascadeType.ALL)
	 @JoinColumn(name = "cc_fk",referencedColumnName = "productId")
	List<ProductVarient> varients;
   
    
}
