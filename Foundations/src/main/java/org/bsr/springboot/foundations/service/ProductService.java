/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: Service layer containing business logic for querying, validating, and creating Product entities based
 *              on client requests.
 * Author: Benjamin Soto-Roberts
 * Created: 01/03/26
 * */

package org.bsr.springboot.foundations.service;

import org.bsr.springboot.foundations.persistence.entity.Product;
import org.bsr.springboot.foundations.persistence.repository.ProductRepository;
import org.bsr.springboot.foundations.presentation.dto.ProductRequestDTO;
import org.bsr.springboot.foundations.presentation.dto.ProductResponseDTO;
import org.bsr.springboot.foundations.presentation.dto.ProductRestRequestDTO;
import org.bsr.springboot.foundations.presentation.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    // Declared final because dependencies are injected once via constructor and never reassigned
    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final BigDecimal STNDRD_RETAIL_MARKUP = BigDecimal.TEN;

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
     */
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

    /**
     * Validates the retail price, constructs a Product entity, applies the standard markup rule to compute vendor
     * price, saves the entity, and returns a response DTO.
     * */
    @Transactional
    public ProductResponseDTO createProductFromRequest(ProductRestRequestDTO requestDTO) {
        // Throws exception if retail price rule returns false
        if (!isValidRetailPrice(requestDTO.retailPrice())) {
            throw new IllegalArgumentException("Retail Price cannot be less than 10 dollars.");
        }
        Product product = mapper.toProduct(requestDTO);
        product.setVendorPrice(product.getRetailPrice().subtract(STNDRD_RETAIL_MARKUP));

        Product saved = repository.save(product);
        return mapper.toResponseDto(saved);

    }

    /**
     * Helper method to check if the retail price input is at the minimum threshold. For convenience of the demo, the
     * vendor price is derived from the input retailPrice minus a standard retail markup value. This ensures that the
     * vendor price is never in the negative. Returns false if the retailPrice is null or less than the standard
     * retail markup.
     * */
    private boolean isValidRetailPrice(BigDecimal retailPrice) {
        return retailPrice != null && retailPrice.compareTo(STNDRD_RETAIL_MARKUP) >= 0;
    }

    /**
     * The method checks if a product exists and returns false if it does not exist otherwise deletes the product and
     * returns true;
     * */
    @Transactional
    public boolean deleteProductFromRequest(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

}
