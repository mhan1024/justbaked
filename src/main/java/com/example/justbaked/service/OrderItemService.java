package com.example.justbaked.service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.stereotype.Service;

import com.example.justbaked.model.CustomerOrder;
import com.example.justbaked.model.Item;
import com.example.justbaked.model.OrderItem;
import com.example.justbaked.repository.CustomerOrderRepository;
import com.example.justbaked.repository.ItemRepository;
import com.example.justbaked.repository.OrderItemRepository;

@Service
public class OrderItemService {
	
	private OrderItemRepository oir;
	private CustomerOrderRepository cor;
	private ItemRepository ir;
	
	public OrderItemService(OrderItemRepository oir, CustomerOrderRepository cor, ItemRepository ir) {
		this.oir = oir;
		this.cor = cor;
		this.ir = ir;
	}
	
	public OrderItem createOrderItem(Integer orderId, Integer itemId, Integer quantity) {
		// Ensure that order and item exist
		CustomerOrder order = cor.findById(orderId).get();
		Item item = ir.findById(itemId).get();
		
		// If either order or item does not exist, throw an error
		if (order == null || item == null) {
			throw new RuntimeException("Order or Item is not found");
		}
		
		
		// Otherwise, create and save the order item 
		OrderItem orderItem = new OrderItem();
		orderItem.setOrder(order);
		orderItem.setItem(item);
		orderItem.setQuantity(quantity);
		
		// Calculate price per item 
		BigDecimal totalPrice = (item.getPrice()).multiply( BigDecimal.valueOf(quantity) );
		orderItem.setPrice(totalPrice);
		
		return oir.save(orderItem);
	}
	
	public Optional<OrderItem> findOrderItemById(Integer id){
		return oir.findById(id);
	}
	
	public List<OrderItem> getAllOrderItems(){
		return oir.findAll();
	}
	
	public List<OrderItem> getItemsByOrderId(Integer orderId){
		return oir.findByOrderId(orderId);
	}
	
	public OrderItem updateOrderItem(Integer id, Item item, Integer quantity) {
		OrderItem oDB = oir.findById(id).get();
		
		if (oDB != null) {
			if(Objects.nonNull(item)) {
				oDB.setItem(item);
			}
			
			if(Objects.nonNull(quantity)) {
				oDB.setQuantity(quantity);
				
				// Update price based on new quantity
				Item i = (item != null) ? item : oDB.getItem();
				BigDecimal newPrice = (i.getPrice()).multiply(BigDecimal.valueOf(quantity));
				oDB.setPrice(newPrice);
			}
		}
		
		return oir.save(oDB);
	}
	
	public void deleteOrderItem(Integer id) {
		oir.deleteById(id);
	}
}
