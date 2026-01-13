/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ProductService is a service class that contains the business logic to return results from the client
 *              requests.
 * Author: Benjamin Soto-Roberts
 * Created: 01/03/26
 * */

package org.bsr.springboot.foundations.service;

import org.bsr.springboot.foundations.persistence.entity.Product;
import org.bsr.springboot.foundations.persistence.repository.ProductRepository;
import org.bsr.springboot.foundations.presentation.dto.ProductRequestDTO;
import org.bsr.springboot.foundations.presentation.dto.ProductResponseDTO;
import org.bsr.springboot.foundations.presentation.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    // Final for thread safety
    private final ProductRepository repository;
    private final ProductMapper mapper;

    /*The ProductRepository is injected into the constructor. SpringBoot automatically injects dependencies into a class
     with a single constructor*/
    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    /**
     * The method takes in the client request and uses the abstract method created in the ProductRepository to check if
     * the clients search term was found in any of the product names then maps the list of products to response
     * dtos to display back to the client.
     * */
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getProductsContaining(ProductRequestDTO productRequest) {
        String searchTerm = productRequest.productName().trim();

        List<Product> products = repository.findAllByProductNameContainingIgnoreCase(searchTerm);

        return products.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * The method selects all rows from the db and maps the products to response dtos.
     * Used for RESTapi calls to return all products.
     * */
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = repository.findAll();

        return products.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * The method selects a product by its id. Returns an optional in case the request is not found.
     * Used for api calls.
     * */
    @Transactional(readOnly = true)
    public Optional<ProductResponseDTO> getProductById(Long id) {
        return repository.findById(id).map(mapper::toResponseDto);
    }

}
