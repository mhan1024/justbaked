package com.example.justbaked.model;

import lombok.*; 
import jakarta.persistence.*;
import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="items")
public class Item {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length = 500)
	private String dessertName;
	
	@Column(precision = 6, scale = 2)
	private java.math.BigDecimal price;
	
	@Column(length = 750)
	private String ingredients;

}
