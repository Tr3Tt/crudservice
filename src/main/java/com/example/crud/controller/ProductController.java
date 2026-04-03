package com.example.crud.controller;

import com.example.crud.entity.Product;
import com.example.crud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Получение всех товаров
    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    // Создание товара
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product saved =  productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Получение товара по id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ResponseEntity.ok(product);
    }
}
