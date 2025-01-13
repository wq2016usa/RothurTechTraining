package com.example.shoppingmodule.service;

import com.example.shoppingmodule.entity.Product;
import com.example.shoppingmodule.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Create a new product
    public Product createProduct(Product product) {
        // Check if a product with the same name already exists
        Product existingProduct = productRepository.findByName(product.getName());
        if (existingProduct != null) {
            throw new RuntimeException("Product with name '" + product.getName() + "' already exists.");
        }

        return productRepository.save(product);
    }

    // Get a product by ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Update a product
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        //if some updated info is empty
        if(updatedProduct.getName()==null || updatedProduct.getPrice()==null || updatedProduct.getQuantity()==null){
            throw new RuntimeException("Invalid product info");
        }

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());

        return productRepository.save(existingProduct);
    }

    // Delete a product
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
