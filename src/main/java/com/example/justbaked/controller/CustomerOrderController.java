package com.example.justbaked.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.justbaked.service.CustomerOrderService;
import com.example.justbaked.model.CustomerOrder;

import java.math.BigDecimal;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/customer-orders")
public class CustomerOrderController {

	private final CustomerOrderService cos;
	
	public CustomerOrderController(CustomerOrderService cos) {
		this.cos = cos;
	}
	
	@GetMapping
	public List<CustomerOrder> getAllOrders(){
		return cos.getAllOrders();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerOrder> getOrderById(@PathVariable Integer id){
		return cos.findOrderById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/customer-id/{customerId}")
	public List<CustomerOrder> getOrderByCustomerId(@PathVariable Integer customerId) {
		return cos.findByCustomer_Id(customerId);
	}
	
	@GetMapping("/status/{id}") 
	public ResponseEntity<String> getStatus(@PathVariable Integer id){
		return cos.getStatusById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}/status")
	public ResponseEntity<String> updateOrderStatus(@PathVariable Integer id, @RequestBody Map<String, String> payload) {
	    String status = payload.get("status");
	    if (status == null) {
	        return ResponseEntity.badRequest().body("Status is required");
	    }

	    cos.updateStatus(id, status);
	    return ResponseEntity.ok("Order status updated successfully");
	}
	
	@PostMapping
	public CustomerOrder createOrder(@RequestBody CustomerOrder order) {
		Integer customerId = order.getCustomer().getId();
		return cos.createOrder(customerId, order.getTotal());
	}
	
	@DeleteMapping("/{id}") 
	public void deleteOrder(@PathVariable Integer id) {
		cos.deleteOrder(id);
	}
}
