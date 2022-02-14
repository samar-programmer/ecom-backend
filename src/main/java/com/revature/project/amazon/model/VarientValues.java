package com.revature.project.amazon.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name="product_varient_values_table")
public class VarientValues {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long varientValuesId;
	private Long varientId;
	private String name;
	private String price;
	private String radioCheck;
	private String email;
	
	 
}
