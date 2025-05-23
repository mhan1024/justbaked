package com.example.justbaked.service;

import java.util.List;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.justbaked.model.Item;
import com.example.justbaked.repository.ItemRepository;


@Service
public class ItemService {
	
	private final ItemRepository ir;
	
	public ItemService(ItemRepository ir) {
		this.ir = ir;
	}
	
	/*
	 *  Save a single item 
	 */
	public Item saveItem(Item i) {
		return ir.save(i);
	}
	
	/*
	 *  Find item by id
	 */
	public Optional<Item> findItemById(Integer id) {
		return ir.findById(id);
	}
	
	public Optional<Item> findItemByName(String dessertName) {
		return ir.findItemByDessertName(dessertName);
	}
	/*
	 *  Get a list of all items
	 */
	public List<Item> getAllItems() {
		return ir.findAll();
	}
	
	/*
	 *  Update an existing item (determined by id) with the updated item (updatedItem)
	 */
	public Item updateItem(Integer id, Item updatedItem) {
		Item itemDB = ir.findById(id).get();
		
		if (updatedItem != null) {
			if (Objects.nonNull(updatedItem.getDessertName())) {
				itemDB.setDessertName(updatedItem.getDessertName());
			}
			
			if(Objects.nonNull(updatedItem.getPrice())) {
				itemDB.setPrice(updatedItem.getPrice());
			}
			
			if (Objects.nonNull(updatedItem.getIngredients())) {
				itemDB.setIngredients(updatedItem.getIngredients());
			}

			return ir.save(itemDB);
			
		}
		

		return null;
	}
	
	/*
	 *  Delete an item given the item's id
	 */
	public void deleteItemById(Integer id) {
		ir.deleteById(id);
	}

}
