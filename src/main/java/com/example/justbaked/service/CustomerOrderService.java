package com.example.justbaked.service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.stereotype.Service;

import com.example.justbaked.model.Customer;
import com.example.justbaked.model.CustomerOrder;
import com.example.justbaked.model.OrderItem;
import com.example.justbaked.repository.CustomerOrderRepository;
import com.example.justbaked.repository.CustomerRepository;
import com.example.justbaked.repository.OrderItemRepository;

@Service
public class CustomerOrderService {

	private final CustomerOrderRepository cosr;
	private final CustomerRepository cr;
	private OrderItemRepository oir;
	
	public CustomerOrderService(CustomerOrderRepository cosr, CustomerRepository cr, OrderItemRepository oir) {
		this.cosr = cosr;
		this.cr = cr;
		this.oir = oir;
	}
	
	public CustomerOrder createOrder(Integer customerId, BigDecimal total) {
		// Ensure that customer exists
		Customer customer = cr.findById(customerId).get();
		
		// If customer does not exist, throw an error
		if (customer == null) {
			throw new RuntimeException("Customer not found");
		}
		
		
		// If the customer exists, create the order
		CustomerOrder order = new CustomerOrder();
		order.setCustomer(customer);
		order.setTotal(total);
		order.setStatus("Pending");
		
		
		return cosr.save(order);
	}
	
	public Optional<CustomerOrder> findOrderById(Integer id) {
		return cosr.findById(id);
	}
	
	public Optional<String> getStatusById(Integer id) {
		return cosr.findById(id)
				.map(CustomerOrder::getStatus);
	}
	
	public List<CustomerOrder> findByCustomer_Id(Integer customerId) {
		return cosr.findByCustomer_Id(customerId);
	}
	
	public List<CustomerOrder> getAllOrders() {
		return cosr.findAll();
	}
	
	public void deleteOrder(Integer id) {
		cosr.deleteById(id);
	}
	
	public void updateOrderTotal(Integer id) {
		CustomerOrder order = cosr.findById(id).get();
		
		if (order == null) {
			throw new RuntimeException("Order not found");
		}
		
		List<OrderItem> items = oir.findByOrderId(id);
		
		// Calculate the total
		BigDecimal newTotal = BigDecimal.valueOf(0.0);
		
		for(OrderItem i : items) {
			newTotal = newTotal.add(i.getPrice());
		}
		
		// Save new total
		order.setTotal(newTotal);
		cosr.save(order);
	}
	
	public void updateStatus(Integer id, String status) {
		CustomerOrder order = cosr.findById(id)
				.orElseThrow(() -> new RuntimeException("Order does not exist"));
		
		order.setStatus(status);
		cosr.save(order);
	}
}
