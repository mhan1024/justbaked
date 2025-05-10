package com.example.justbaked.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

// id, customerName

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customers")
public class Customer {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length = 500)
	private String customerName;
	
	/*
	 *  Not an actual column in the table, but just
	 *  a Java side representation
	 */ 
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<CustomerOrder> orders;
	
}
