/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ProductMapper is a manual mapper class that manually map the ProductRequest and ProductResponse dto
 *              fields without the use of a third party library for training purposes. Otherwise the recommended
 *              approach would be to use MapStruct - a powerful, annotation-based code generator that automatically
 *              creates efficient, boilerplate-free mapping code at compile time.
 * Author: Benjamin Soto-Roberts
 * Created: 01/09/26
 * */

package org.bsr.springboot.foundations.presentation.mapper;


import org.bsr.springboot.foundations.persistence.entity.Product;
import org.bsr.springboot.foundations.presentation.dto.ProductResponseDTO;
import org.bsr.springboot.foundations.presentation.dto.ProductRestRequestDTO;
import org.springframework.stereotype.Component;


// Uses Component stereotype for bean management
@Component
public class ProductMapper {


    /**
     * This method takes in a product, does a null check and maps the product fields to the appropriate
     * ProductResponseDTO fields and returns a response dto.
     * */
    public ProductResponseDTO toResponseDto(Product product) {
        if  (product == null) {
            return null;
        }
        return new ProductResponseDTO(
                product.getId(),
                product.getProductName(),
                product.getProductDesc(),
                product.getRetailPrice()
        );
    }


    /**
     * This method takes a DTO request from the ProductRestController and maps it to a product entity.
     * */
    public Product toProduct(ProductRestRequestDTO requestDTO) {
        return new Product(requestDTO.productName(),
                requestDTO.productDesc(),
                requestDTO.retailPrice());
    }

}
