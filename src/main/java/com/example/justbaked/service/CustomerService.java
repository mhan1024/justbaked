package com.example.justbaked.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.justbaked.model.Customer;
import com.example.justbaked.repository.CustomerOrderRepository;
import com.example.justbaked.repository.CustomerRepository;

@Service
public class CustomerService {
	
	private final CustomerRepository cr;
	private final CustomerOrderRepository cor;
	
	public CustomerService(CustomerRepository cr, CustomerOrderRepository cor) {
		this.cr = cr;
		this.cor = cor;
	}
	
	public Customer saveCustomer(Customer c) {
		return cr.save(c);
	}
	
	public Optional<Customer> findCustomerById(Integer id){
		return cr.findById(id);
	}
	
	public Optional<Customer> findCustomerByName(String name){
		return cr.findByCustomerName(name);
	}
	
	public Optional<String> getCustomerNameByOrderId(Integer orderId) {
		return cor.findById(orderId)
				.map(order -> order.getCustomer().getCustomerName());
	}
	
	// Get a list of all customers
	public List<Customer> getAllCustomers() {
		return cr.findAll();
	}
	
	public Customer updateCustomer(Integer id, Customer updatedC) {
		// Get customer from DB
		Customer customerDB = cr.findById(id)
				.orElseThrow(() -> new RuntimeException("Customer does not exist"));
		
		if(Objects.nonNull(updatedC.getCustomerName())) {
			customerDB.setCustomerName(updatedC.getCustomerName());
		}
		
		return cr.save(customerDB);
		
	}
	
	public void deleteCustomer(Integer id) {
		cr.deleteById(id);
	}
}
