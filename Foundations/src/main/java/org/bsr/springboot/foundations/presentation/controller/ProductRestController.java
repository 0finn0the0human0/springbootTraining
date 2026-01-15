/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ProductRestController is a REST api that exposes a REST endpoint for running CRUD operations on
 *              Product entities. Returns JSON responses with appropriate HTTP status codes.
 * Author: Benjamin Soto-Roberts
 * Created: 01/03/26
 * */

package org.bsr.springboot.foundations.presentation.controller;

import org.bsr.springboot.foundations.presentation.dto.ProductResponseDTO;
import org.bsr.springboot.foundations.presentation.dto.ProductRestRequestDTO;
import org.bsr.springboot.foundations.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
     * The method returns an available product by its id. If the product exists, a 200 OK response with the mapped
     * ProductResponseDTO is returned. If not found, a 404 Not Found response is returned.
     * */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        LOGGER.warn("Product id {} was called from REST", id);
        return productService.getProductById(id).map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    /** The method creates a new product and returns 201 Created with a Location header. */
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRestRequestDTO requestDTO) {
        ProductResponseDTO createdProduct = productService.createProductFromRequest(requestDTO);

        // Returns 201 created and the uri of the resource
        return ResponseEntity.created(URI.create("/api/products/" + createdProduct.id())).body(createdProduct);
    }

    /**
     * The method takes an id from a delete request and does a boolean check to return false if not exists or delete
     * and return true. A ternary operation returns 204 for a successful DELETE when there’s nothing to return in the
     * body and 404 when the resource was not found.
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProductFromRequest(id);

        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * The method updates an existing product. Takes the Id of the product from the url path and a requestDTO containing
     * the updated product data. Returns  200 ok with the updated product if found or a 404 not found if the id doesnt
     * exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,
                                                            @RequestBody ProductRestRequestDTO requestDTO) {
        return productService.updateProduct(id, requestDTO).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
