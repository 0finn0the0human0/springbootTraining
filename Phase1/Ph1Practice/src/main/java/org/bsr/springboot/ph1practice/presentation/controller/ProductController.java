package org.bsr.springboot.ph1practice.presentation.controller;

import org.bsr.springboot.ph1practice.entity.Product;
import org.bsr.springboot.ph1practice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;


    @GetMapping("/products/")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productRepository.findById(id).get();
    }

    @PostMapping("/products/")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }
}
