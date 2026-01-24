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
import org.bsr.springboot.foundations.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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


}
