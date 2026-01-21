/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ProductRestControllerTest class uses @WebMvcTest to perform slice tests for the
 *              ProductRestController. Performs acceptance testing for AC-1 Create Product, AC-2 Get Product by id,
 *              AC-3 Update Product and AC-4 Delete Product as defined in Project 1 SRS.
 * Author: Benjamin Soto-Roberts
 * Created: 01/19/26
 * */

package org.bsr.springboot.foundations.presentation.api;

import org.bsr.springboot.foundations.presentation.controller.ProductRestController;
import org.bsr.springboot.foundations.presentation.dto.ProductResponseDTO;
import org.bsr.springboot.foundations.presentation.dto.ProductRestRequestDTO;
import org.bsr.springboot.foundations.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
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

    /**
     * Testing AC-1 Create Product. Creating a product via POST /api/products returns 201 Created and a JSON body
     * containing id, name, description, price.
     * */
    @Test
    void shouldCreateProduct_whenPostRequestIsValid() throws Exception {

        // Arranging the test data
        ProductRestRequestDTO requestDTO = new ProductRestRequestDTO(
                "New Test",
                "A New Test Item",
                new BigDecimal("19.99")
        );

        ProductResponseDTO responseDTO = new ProductResponseDTO(
                1L,
                "New Test",
                "A New Test Item",
                new BigDecimal("19.99")
        );

        // The mock behavior -> if the controller is called with this requestDTO, return this responseDTO.
        when(productService.createProductFromRequest(requestDTO)).thenReturn(responseDTO);

        // Sends a fake HTTP POST request to the controller and asserts that the JSON response matches what is expected
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                        "productName":"New Test",
                        "productDesc":"A New Test Item",
                        "retailPrice":19.99
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseDTO.id()))
                .andExpect(jsonPath("$.productName").value(responseDTO.productName()))
                .andExpect(jsonPath("$.productDesc").value(responseDTO.productDesc()))
                .andExpect(jsonPath("$.retailPrice").value(responseDTO.retailPrice()));
    }

    /**
     * Testing AC-2 Get Product by id. A valid id returns a 200 OK with and a JSON body containing id, name,
     * description, price.
     * */
    @Test
    void shouldReturnProductById() throws Exception {

        // Arranging the test data
        Long productId = 1L;
        String productName = "Widget";
        String productDesc = "A new widget";
        BigDecimal retailPrice = new BigDecimal("19.99");

        ProductResponseDTO responseDTO = new ProductResponseDTO(productId, productName, productDesc, retailPrice);

        // Mock service behavior -> when the controller requests this ID, return the sample product
        when(productService.getProductById(productId)).thenReturn(Optional.of(responseDTO));

        // Sends a fake HTTP GET request to the controller and asserts that the JSON response matches what is expected
        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.productName").value(productName))
                .andExpect(jsonPath("$.productDesc").value(productDesc))
                .andExpect(jsonPath("$.retailPrice").value(retailPrice));
    }

}