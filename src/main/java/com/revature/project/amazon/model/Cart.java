package com.revature.project.amazon.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
@Entity
@Table(name="cart_table")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bufcartId;
	@Column(name = "order_id", nullable = true)
	private int orderId;
	private String email;
	@Column(name = "date_added")
	private Date dateAdded;
	private int quantity;
	private double price;
	@Column(name = "product_id")
	private int productId;
	private String productname;
	private String total;


	
}
