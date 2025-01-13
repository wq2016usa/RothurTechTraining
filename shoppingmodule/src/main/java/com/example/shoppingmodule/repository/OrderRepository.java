package com.example.shoppingmodule.repository;

import com.example.shoppingmodule.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Fetch all orders by user ID
    List<Order> findByUserId(Long userId);
}
