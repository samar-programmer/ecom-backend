package com.revature.project.amazon.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product_varient_table")
public class ProductVarient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long varientId;
	private int productId;
	private String value;
	private String email;
	private String model;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	 @JoinColumn(name = "vc_fk",referencedColumnName = "varientId")
	List<VarientValue> varientvalues;
	
	
}
