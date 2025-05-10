package com.example.justbaked.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.justbaked.service.CustomerService;
import com.example.justbaked.model.Customer;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	private final CustomerService cs;
	
	public CustomerController(CustomerService cs) {
		this.cs = cs;
	}
	
	@GetMapping
	public List<Customer> getAllCustomers(){
		return cs.getAllCustomers();
	}
	
	@GetMapping("/customer-id/{id}") 
	public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
		return cs.findCustomerById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/customer-name/{customerName}")
	public ResponseEntity<Customer> getCustomerByName(@PathVariable String customerName) {
		return cs.findCustomerByName(customerName)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/customer-name-by-order/{orderId}")
	public ResponseEntity<String> getCustomerNameByOrderId(@PathVariable Integer orderId) {
		return cs.getCustomerNameByOrderId(orderId)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public Customer createCustomer(@RequestBody Customer c) {
		// Check if the customer is already in the DB
		Optional<Customer> customer = cs.findCustomerByName(c.getCustomerName());
		
		if (customer.isPresent()) {
			// If customer exists, return the customer from the DB
			return customer.get();
		}
		
		// Otherwise, save the customer
		return cs.saveCustomer(c);
	}
	
	@PutMapping("/{id}") 
	public ResponseEntity<?> updateCustomer(@RequestBody Customer c, @PathVariable Integer id) {
		try {
			return ResponseEntity.ok(cs.updateCustomer(id, c));
			
		} catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}") 
	public void deleteCustomerById(@PathVariable Integer id) {
		cs.deleteCustomer(id);
	}
}
