package com.example.justbaked.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.justbaked.service.OrderItemService;
import com.example.justbaked.model.Item;
import com.example.justbaked.model.OrderItem;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
	
	private final OrderItemService ois;
	
	public OrderItemController(OrderItemService ois) {
		this.ois = ois;
	}
	
	@GetMapping
	public List<OrderItem> getAllOrderItems(){
		return ois.getAllOrderItems();
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<OrderItem> getOrderItemsById(@PathVariable Integer id){
		return ois.findOrderItemById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/order-id/{orderId}") 
	public List<OrderItem> getItemsByOrderId(@PathVariable Integer orderId) {
		return ois.getItemsByOrderId(orderId);
	}
	
	@PostMapping
	public OrderItem createOrderItem(@RequestBody OrderItem o) {
		Integer orderId = o.getOrder().getId();
		Integer itemId = o.getItem().getId();
		Integer quantity = o.getQuantity();
		
		return ois.createOrderItem(orderId, itemId, quantity);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Integer id, @RequestBody OrderItem o) {
		Item item = o.getItem();
		Integer quantity = o.getQuantity();
		
		OrderItem updated = ois.updateOrderItem(id, item, quantity);
		
		return ResponseEntity.ok(updated);
	}
	
	@DeleteMapping("/{id}")
	public void deleteOrderItem(@PathVariable Integer id) {
		ois.deleteOrderItem(id);
	}
}
