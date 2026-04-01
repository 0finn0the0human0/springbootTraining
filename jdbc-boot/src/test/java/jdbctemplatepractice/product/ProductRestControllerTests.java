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
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductRestController.class)
@Import(GlobalExceptionHandler.class)
class ProductRestControllerTests {

    private final UUID testId1 = UUID.randomUUID();
    private final UUID testId2 = UUID.randomUUID();

    @Autowired
    private ObjectMapper objectMapper; // for mapping the json

    private final ProductResponseDTO testProduct1 = new ProductResponseDTO(testId1, "Test Product 1",
            "Description 1...", new BigDecimal("19.99"), new BigDecimal("24.98"));

    private final ProductResponseDTO testProduct2 = new ProductResponseDTO(testId2, "Test Product 2",
            "Description 2...", new BigDecimal("17.99"), new BigDecimal("22.48"));

    private final ProductRequestDTO testRequest = new ProductRequestDTO("Test Product 2",
            "Description 2...", new BigDecimal("17.99"));

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
        
        when(productService.getAllProducts()).thenReturn(Arrays.asList(testProduct1, testProduct2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productName").value(testProduct1.productName()));
    }

    /**
     * Testing requests for getProduct through the controller where product id is not found. ControllerAdvice intercepts
     * ProductNotFoundException thrown by the service mock and maps it to a 404 ProblemDetail response.
     * */
    @Test
    void shouldReturn404_whenProductNotFound() throws Exception {

        when(productService.getProductById(testId1)).thenThrow(new ProductNotFoundException("Product not found: " +
                testId1));

        mockMvc.perform(get("/api/products/{id}", testId1).accept(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Product Not Found"))
                .andExpect(jsonPath("$.type").value("errors/product-not-found"))
                .andExpect(jsonPath("$.instance").value("/api/products/"+testId1));
    }

    /**
     * Testing requests for getProduct through the controller where input is not a valid type. .
     * */
    @Test
    void shouldReturn400_whenTypeMismatch() throws Exception{
        mockMvc.perform(get("/api/products/{id}", "bad-id")
                        .accept(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Invalid Parameter Type"))
                .andExpect(jsonPath("$.type").value("errors/type-mismatch"))
                .andExpect(jsonPath("$.instance").value("/api/products/bad-id"));
    }

    /**
     * Testing requests for getProduct through the controller when request is valid
     * */
    @Test
    void shouldReturnProduct_whenRequestIsValid() throws Exception{

        when(productService.getProductById(testId1)).thenReturn(testProduct1);

        mockMvc.perform(get("/api/products/{id}", testId1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productName").value(testProduct1.productName()))
                .andExpect(jsonPath("$.productDesc").value(testProduct1.productDesc()))
                .andExpect(jsonPath("$.vendorPrice").value(testProduct1.vendorPrice()))
                .andExpect(jsonPath("$.retailPrice").value(testProduct1.retailPrice()));

    }

    /**
     * Testing request for postProduct through the controller. Verifies location header.
     * */
    @Test
    void shouldCreateProduct_whenRequestIsValid() throws Exception {

        when(productService.postProduct(testRequest)).thenReturn(testProduct2);

        mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/products/"+testId2))
                .andExpect(jsonPath("$.uuid").value(testId2.toString()))
                .andExpect(jsonPath("$.productName").value(testProduct2.productName()))
                .andExpect(jsonPath("$.productDesc").value(testProduct2.productDesc()))
                .andExpect(jsonPath("$.vendorPrice").value(testProduct2.vendorPrice()))
                .andExpect(jsonPath("$.retailPrice").value(testProduct2.retailPrice()));



    }

    /**
     * Tests putProduct to ensure product update and 200 Ok status
     * */
    @Test
    void shouldUpdateProduct_whenRequestIsValid() throws Exception{

        ProductResponseDTO updatedProduct = new ProductResponseDTO(testId2, "Test Product 1",
                "Description 1...", new BigDecimal("19.99"), new BigDecimal("24.98"));

        when(productService.putProduct(testId2, testRequest)).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/{id}", testId2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("uuid").value(testId2.toString()))
                .andExpect(jsonPath("productName").value(updatedProduct.productName()))
                .andExpect(jsonPath("productDesc").value(updatedProduct.productDesc()))
                .andExpect(jsonPath("vendorPrice").value(updatedProduct.vendorPrice()))
                .andExpect(jsonPath("retailPrice").value(updatedProduct.retailPrice()));
    }

    /**
     * Tests deleteProductById to ensure product deletes and no content status
     * */
    @Test
    void shouldDeleteProduct_whenProductIsFound() throws Exception{

        mockMvc.perform(delete("/api/products/{id}", testId1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify the service was called with the correct id
        verify(productService).deleteProductById(testId1);


    }


}
