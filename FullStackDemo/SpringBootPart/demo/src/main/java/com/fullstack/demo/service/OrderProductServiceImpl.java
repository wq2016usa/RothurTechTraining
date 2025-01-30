package com.fullstack.demo.service;

import com.fullstack.demo.entity.OrderProduct;
import com.fullstack.demo.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductServiceImpl implements OrderProductService {
	@Autowired
	OrderProductRepository orderProductRepository;

	@Override
	public OrderProduct createOrderProduct(OrderProduct orderProduct) {
		return orderProductRepository.save(orderProduct);
	}
}
