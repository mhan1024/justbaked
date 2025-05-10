package com.example.justbaked.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.justbaked.model.CustomerOrder;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer>{

	List<CustomerOrder> findByCustomer_Id(Integer customerId);

}
