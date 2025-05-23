package com.example.justbaked.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.justbaked.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	Optional<Item> findItemByDessertName(String dessertName);

}

