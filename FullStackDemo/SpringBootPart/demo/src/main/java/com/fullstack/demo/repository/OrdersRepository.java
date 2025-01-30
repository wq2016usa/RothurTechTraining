package com.fullstack.demo.repository;

import java.util.List;

import com.fullstack.demo.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer>{
//	List<Orders> findByOrderUserId(Integer orderUserId);
	List<Orders> findOrdersByEuser_UserId(Integer userId);
}
