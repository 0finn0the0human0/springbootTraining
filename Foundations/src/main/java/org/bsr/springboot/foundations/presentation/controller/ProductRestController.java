/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ProductRestController is a REST api that exposes a REST endpoint for running CRUD operations on
 *              Product entities.
 * Author: Benjamin Soto-Roberts
 * Created: 01/03/26
 * */

package org.bsr.springboot.foundations.presentation.controller;

import org.bsr.springboot.foundations.persistence.entity.Product;
import org.bsr.springboot.foundations.persistence.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductRepository repository;

    public ProductRestController(ProductRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<Product> getProducts() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable  Long id) {
        return repository.findById(id).get();
    }

}
