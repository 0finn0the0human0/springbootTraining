/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ProductRestController is a REST api that exposes a REST endpoint for running CRUD operations on
 *              Product entities. Returns JSON responses with appropriate HTTP status codes.
 * Author: Benjamin Soto-Roberts
 * Created: 01/03/26
 * */

package org.bsr.springboot.foundations.presentation.controller;

import org.bsr.springboot.foundations.presentation.dto.ProductResponseDTO;
import org.bsr.springboot.foundations.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    // A static logger to log important transactions
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

    // Final for thread safety
    private final ProductService productService;

    /*The ProductService is injected into the constructor. SpringBoot automatically injects dependencies into a class
     with a single constructor*/
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * The method returns all available products as a ResponseEntity containing a list of ProductResponseDTO objects
     * and an HTTP 200 OK status.
     * */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        LOGGER.warn("getProducts() called from REST");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * The method returns an available product by its id.  If the product exists, a 200 OK response with the mapped
     * ProductResponseDTO is returned. If not found, a 404 Not Found response is returned.
     * */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        LOGGER.warn("Product id {} was called from REST", id);
        return productService.getProductById(id).map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build());
    }


}
