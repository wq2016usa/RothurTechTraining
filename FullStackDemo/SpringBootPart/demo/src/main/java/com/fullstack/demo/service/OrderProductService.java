package com.fullstack.demo.service;

import com.fullstack.demo.entity.OrderProduct;
import org.springframework.stereotype.Service;

@Service
public interface OrderProductService {
	public OrderProduct createOrderProduct(OrderProduct orderProduct);
}
