package com.example.justbaked.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

// id, customer_id (fk), total
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customer_orders")
public class CustomerOrder {

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	@JsonBackReference
	private Customer customer;
	
	@Column(precision = 8, scale = 2)
	private java.math.BigDecimal total;
	
	@Column(length = 250)
	private String status;
}
