/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ProductWebControllerTest class uses @WebMvcTest to perform slice tests for the
 *              ProductWebController. Performs acceptance testing for AC-5 Web (Thymeleaf) UI as defined in Project 1
 *              SRS.
 * Author: Benjamin Soto-Roberts
 * Created: 01/23/26
 * */

package org.bsr.springboot.foundations.presentation.ui;

import org.bsr.springboot.foundations.presentation.controller.ProductWebController;
import org.bsr.springboot.foundations.presentation.dto.ProductResponseDTO;
import org.bsr.springboot.foundations.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(ProductWebController.class)
public class ProductWebControllerTest {

    private final MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    // @Autowired IS required here for test constructor injection because test classes are not Spring-managed components
    @Autowired
    public ProductWebControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }


    /**
     * Testing AC-5 UI Controller returns the correct Thymeleaf template and the model contains the DTO for the form
     * */
    @Test
    void shouldShowFormPage() throws Exception{

        mockMvc.perform(get("/"))
                .andExpect(status().isOk()) // Verifies status return is a 200 OK
                .andExpect(view().name("form")) // Verifies controller returns correct template
                .andExpect(model().attributeExists("productNameQuery")); // Verifies the object exists in model
    }

    @Test
    void shouldReturnResultsPage_whenProductsFound() throws Exception{

        // Arranging the test data
        ProductResponseDTO testProduct1 = new ProductResponseDTO(1L, "Comedy DVD", "Funny",
                new BigDecimal("1.69"));

        ProductResponseDTO testProduct2 = new ProductResponseDTO(2L, "Action DVD",
                "Action packed", new BigDecimal("1.69"));

        // The mock behavior -> if there is any return on getProductsContaining then return the list of test data
        when(productService.getProductsContaining(any())).thenReturn(List.of(testProduct1, testProduct2));

        mockMvc.perform(post("/").param("productName", "dvd"))
                .andExpect(status().isOk()) // Verifies status return is a 200 OK
                .andExpect(view().name("results")) // Verifies controller returns correct template
                .andExpect(model().attributeExists("products")) // Verifies the object exists in the model
                .andExpect(model().attribute("products", hasSize(2))); // Verifies size of return list
    }
}
