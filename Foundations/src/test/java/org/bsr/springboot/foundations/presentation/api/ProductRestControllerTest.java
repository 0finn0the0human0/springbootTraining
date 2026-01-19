package org.bsr.springboot.foundations.presentation.api;

import org.bsr.springboot.foundations.presentation.controller.ProductRestController;
import org.bsr.springboot.foundations.presentation.dto.ProductResponseDTO;
import org.bsr.springboot.foundations.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;  // ← CHANGED PACKAGE
import org.springframework.test.context.bean.override.mockito.MockitoBean;  // ← CHANGED PACKAGE
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    private final MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    // @Autowired IS required here for test constructor injection
    @Autowired
    ProductRestControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void shouldReturnProductById() throws Exception {
        // Arrange
        Long productId = 1L;
        String productName = "Widget";
        String productDesc = "A new widget";
        BigDecimal productPrice = new BigDecimal("19.99");

        ProductResponseDTO mockProduct = new ProductResponseDTO(productId, productName, productDesc, productPrice);

        when(productService.getProductById(productId)).thenReturn(Optional.of(mockProduct));

        // Act & Assert
        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value(productName))
                .andExpect(jsonPath("$.retailPrice").value(productPrice));
    }
}