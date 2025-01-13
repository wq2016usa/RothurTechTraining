package com.example.shoppingmodule.service;

import com.example.shoppingmodule.entity.*;
import com.example.shoppingmodule.repository.OrderProductRepository;
import com.example.shoppingmodule.repository.OrderRepository;
import com.example.shoppingmodule.repository.ProductRepository;
import com.example.shoppingmodule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    // Create a new order with product quantities
    public Order createOrder(Long userId, List<OrderProductRequest> orderProductRequests) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Create a new Order
        Order order = new Order();
        order.setUser(user);

        // Initialize total amount
        Double totalAmount = 0.0;

        // Create and add OrderProduct entries
        for (OrderProductRequest request : orderProductRequests) {
            // Fetch the product for the given product ID
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));

            // Check if requested quantity is available
            int updatedQuantity = product.getQuantity() - request.getQuantity();
            if (updatedQuantity < 0) {
                throw new RuntimeException("Insufficient stock for product with id: " + product.getId());
            }

            // Update the product's stock
            product.setQuantity(updatedQuantity);
            productRepository.save(product);

            //create OrderProduct object
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(request.getQuantity());
            order.addOrderProduct(orderProduct); // Use helper method to maintain relationship

            // Update the total amount
            totalAmount += product.getPrice() * request.getQuantity();
        }

        // Set the total amount for the order
        order.setTotalAmount(totalAmount);

        // Save the order (cascades will save associated OrderProducts)
        return orderRepository.save(order);
    }

    // Update an existing order
    public Order updateOrder(Long orderId, List<OrderProductRequest> orderProductRequests) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // Clear the existing products using the helper method
        List<OrderProduct> existingOrderProducts = new ArrayList<>(order.getOrderProducts());
        for (OrderProduct existingOrderProduct : existingOrderProducts) {
            order.removeOrderProduct(existingOrderProduct);
        }

        // Add new products to the order
        double totalAmount = 0.0;
        for (OrderProductRequest request : orderProductRequests) {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(request.getQuantity());
            order.addOrderProduct(orderProduct); // Use the helper method

            totalAmount += product.getPrice() * request.getQuantity();
        }

        // Update the total amount
        order.setTotalAmount(totalAmount);

        // Save the updated order
        return orderRepository.save(order);
    }

    // get all orders for a specific user
    public List<Order> getOrdersByUserId(Long userId) {
        boolean userExists = userRepository.existsById(userId);

        if (!userExists) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        return orderRepository.findByUserId(userId);
    }

    // Delete an order by ID
    public void deleteOrderById(Long orderId) {
        // Check if the order exists
        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found with id: " + orderId);
        }

        // Delete the order
        orderRepository.deleteById(orderId);
    }
}
