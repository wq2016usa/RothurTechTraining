package com.fullstack.demo.service;

import java.util.List;

import com.fullstack.demo.entity.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
	public List<Product> getAllProducts();
	public Product create(Product product) throws Exception;
	public Product update(Product product) throws Exception; 
	public Product findProductById(Integer id);
	public boolean deleteProduct(Integer id) throws Exception;

}
