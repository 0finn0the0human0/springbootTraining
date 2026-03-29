/**
 * Project: JDBCTemplate Practice
 * Description: Slice tests the ProductRestController
 * Author: Benjamin Soto-Roberts
 * Created: 03/28/2026
 */
package jdbctemplatepractice.product;

import jdbctemplatepractice.common.GlobalExceptionHandler;
import jdbctemplatepractice.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductRestController.class)
@Import(GlobalExceptionHandler.class)
class ProductRestControllerTests {

    private final UUID testId1 = UUID.randomUUID();
    private final UUID testId2 = UUID.randomUUID();

    private final MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    ProductRestControllerTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /**
     * Testing valid requests for getAllProducts through the controller
     * */
    @Test
    void shouldReturnAllProducts() throws Exception{
        ProductResponseDTO p1 = new ProductResponseDTO(testId1, "Test Product 1",
                "Description 1...", new BigDecimal("19.99"), new BigDecimal("24.98"));

        ProductResponseDTO p2 = new ProductResponseDTO(testId2, "Test Product 2",
                "Description 2...", new BigDecimal("17.99"), new BigDecimal("22.48"));

        when(productService.getAllProducts()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productName").value("Test Product 1"));
    }

    /**
     * Testing requests for getProduct through the controller where product id is not found. ControllerAdvice intercepts
     * ProductNotFoundException thrown by the service mock and maps it to a 404 ProblemDetail response.
     * */
    @Test
    void shouldReturn404_whenProductNotFound() throws Exception {

        when(productService.getProductById(testId1)).thenThrow(new ProductNotFoundException("Product not found: " +
                testId1));

        mockMvc.perform(get("/api/products/{testId1}", testId1).accept(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Product Not Found"))
                .andExpect(jsonPath("$.type").value("errors/product-not-found"))
                .andExpect(jsonPath("$.instance").value("/api/products/"+testId1));
    }
}
