package com.revature.project.amazon.model;

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
@Entity
@Setter
@Getter
@Table(name="USERS_TABLE")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userid;
	
	@Column(unique=true)
	private String email;
	private String firstname;
	private String lastname;
	private String password;
	private int age;
	private String role;
	private String address;
	private long otp;
}
