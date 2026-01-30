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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    private final MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    // @Autowired IS required here for test constructor injection because test classes are not Spring-managed components
    @Autowired
    ProductRestControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /**
     * Testing AC-1 Create Product. Creating a product via POST /api/products returns 201 Created and a JSON body
     * containing id, name, description, price.
     *
     */
    @Test
    void shouldCreateProduct_whenPostRequestIsValid() throws Exception {

        // Arranging the test data
        ProductRestRequestDTO requestDTO = new ProductRestRequestDTO("AC-1 Test",
                "A New Test Item", new BigDecimal("19.99"));

        ProductResponseDTO responseDTO = new ProductResponseDTO(1L, "AC-1 Test",
                "A New Test Item", new BigDecimal("19.99"));

        // The mock behavior -> if the controller is called with this requestDTO, return this responseDTO.
        when(productService.createProductFromRequest(requestDTO)).thenReturn(responseDTO);

        // Sends a fake HTTP POST request to the controller and asserts that the JSON response matches what is expected
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "productName":"AC-1 Test",
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
     *
     */
    @Test
    void shouldReturnProductById() throws Exception {

        // Arranging the test data
        Long productId = 1L;
        String productName = "AC-2 Test";
        String productDesc = "A new test item";
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

    /**
     * Testing AC-3 PUT request. Updates the product, price is normalized to two decimal places and response
     * reflects updated values
     *
     */
    @Test
    void shouldUpdateProduct_whenPutRequestIsValid() throws Exception {
        // Arranging the test data
        Long productId = 1L;
        ProductRestRequestDTO restRequestDTO = new ProductRestRequestDTO("AC-3 Test Updated",
                "A new test item", new BigDecimal("19.99"));

        ProductResponseDTO responseDTO = new ProductResponseDTO(productId, "AC-3 Test Updated",
                "A new test item", new BigDecimal("19.99"));

        // Mock service behavior -> when the controller requests this ID with updated fields, return the sample product
        when(productService.updateProduct(productId, restRequestDTO)).thenReturn(Optional.of(responseDTO));

        // Sends a fake HTTP PUT request to the controller and asserts that the JSON response matches what is expected
        mockMvc.perform(put("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "productName":"AC-3 Test Updated",
                                "productDesc":"A new test item",
                                "retailPrice":19.99
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.productName").value(responseDTO.productName()))
                .andExpect(jsonPath("$.productDesc").value(responseDTO.productDesc()))
                .andExpect(jsonPath("$.retailPrice").value(responseDTO.retailPrice()));
    }

    /**
     * Testing AC-4 Delete product. DELETE returns 204 No Content Product no longer appears in subsequent listings
     */
    @Test
    void shouldDeleteProduct_whenIdExists() throws Exception {
        // Arranging the test data
        Long productId = 1L;

        // Mock service behavior: product exists → delete returns true
        when(productService.deleteProductFromRequest(productId)).thenReturn(true);

        // Sends a fake HTTP DELETE request to the controller and asserts that the status return is No Content.
        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());

    }
}