/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ProductServiceTest class isolates the service-layer business logic by mocking the ProductRepository
 *              and ProductMapper dependencies. Ensures correct behavior for querying, validating, creating, updating,
 *              and deleting Product entities without loading the Spring application context.
 * Author: Benjamin Soto-Roberts
 * Created: 01/26/26
 * */

package org.bsr.springboot.foundations.service;

import org.bsr.springboot.foundations.persistence.entity.Product;
import org.bsr.springboot.foundations.persistence.repository.ProductRepository;
import org.bsr.springboot.foundations.presentation.dto.ProductRequestDTO;
import org.bsr.springboot.foundations.presentation.dto.ProductResponseDTO;
import org.bsr.springboot.foundations.presentation.dto.ProductRestRequestDTO;
import org.bsr.springboot.foundations.presentation.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class) // Pure Java unit test, doesnt load Spring
public class ProductServiceTest {

    // Mocks Dependencies for ProductRepository and ProductMapper
    @Mock
    ProductRepository repository;
    @Mock
    ProductMapper mapper;

    @InjectMocks
    ProductService service;

    /**
     * Testing service logic for getProductsContaining. Verify that getProductsContaining() trims the search term,
     * calls the repository correctly, maps the results, and returns the mapped list.
     * */
    @Test
    void shouldReturnMappedProduct_whenSearchMatches() {

        // Arranging the test data
        // Incoming request contains extra spaces that should be trimmed
        ProductRequestDTO request = new ProductRequestDTO("  dvd  ");

        // Fake Product entity returned by the repository
        Product entity = new Product();
        entity.setProductName("Comedy DVD");
        entity.setProductDesc("Funny");
        entity.setRetailPrice(new BigDecimal("1.69"));

        // Fake DTO returned by the mapper
        ProductResponseDTO testProduct = new ProductResponseDTO(1L, "Comedy DVD", "Funny",
                new BigDecimal("10.69"));

        // The mock behavior -> repository returns list containing the entity
        when(repository.findAllByProductNameContainingIgnoreCase("dvd")).thenReturn(List.of(entity));

        // The mock behavior -> mapper converts entity -> DTO
        when(mapper.toResponseDto(entity)).thenReturn(testProduct);

        // Calls the service method under test
        List<ProductResponseDTO> results = service.getProductsContaining(request);

        // Verify the service returns the mapped DTO list
        assertEquals(1, results.size(), "Expected exactly one result");
        assertEquals("Comedy DVD", results.getFirst().productName());

        // Verify repository was called with the trimmed search term
        verify(repository).findAllByProductNameContainingIgnoreCase("dvd");
        verify(mapper).toResponseDto(entity); // Verify mapper was used to convert the entity
    }


    /**
     * Testing service logic for getAllProducts. Verify that getAllProducts calls the repository correctly, maps the
     * results, and returns the mapped list for all test products.
     * */
    @Test
    void shouldReturnAllProducts_whenProductsExist() {

        // Arranging the test data
        Product entity1 = new Product();
        entity1.setProductName("Comedy DVD");
        entity1.setProductDesc("Funny");
        entity1.setRetailPrice(new BigDecimal("10.69"));

        Product entity2 = new Product();
        entity2.setProductName("Action DVD");
        entity2.setProductDesc("Action packed");
        entity2.setRetailPrice(new BigDecimal("10.69"));

        // Fake DTO returned by the mapper
        ProductResponseDTO testProduct1 = new ProductResponseDTO(1L, "Comedy DVD", "Funny",
                new BigDecimal("10.69"));

        // Fake DTO returned by the mapper
        ProductResponseDTO testProduct2 = new ProductResponseDTO(2L, "Action DVD",
                "Action packed", new BigDecimal("10.69"));

        // The mock behavior -> repository returns all entities
        when(repository.findAll()).thenReturn(List.of(entity1, entity2));

        // The mock behavior -> mapper converts entity -> DTO
        when(mapper.toResponseDto(entity1)).thenReturn(testProduct1);
        when(mapper.toResponseDto(entity2)).thenReturn(testProduct2);

        // Calls the service method under test
        List<ProductResponseDTO> results = service.getAllProducts();

        // Verify the service returns the mapped DTO list
        assertEquals(2, results.size());
        assertEquals("Comedy DVD", results.getFirst().productName());
        assertEquals("Action DVD", results.getLast().productName());

        // Verify repository was called
        verify(repository).findAll();

        // Verify mapper was used to convert the entity
        verify(mapper).toResponseDto(entity1);
        verify(mapper).toResponseDto(entity2);


    }

    /**
     * Testing service logic for getProductById. Verify that getProductById calls the repository correctly, maps the
     * result, and returns the mapped ProductResponseDTO for the test product.
     * */
    @Test
    void shouldReturnProduct_whenProductIdIsFound() {

        // Arranging the test data
        Product entity = new Product();
        entity.setProductName("Comedy DVD");
        entity.setProductDesc("Funny");
        entity.setRetailPrice(new BigDecimal("10.69"));

        Long id = 1L;

        // Fake DTO returned by the mapper
        ProductResponseDTO testProduct = new ProductResponseDTO(1L, "Comedy DVD", "Funny",
                new BigDecimal("10.69"));

        // The mock behavior -> repository returns the entity by id
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        // The mock behavior -> mapper converts entity -> DTO
        when(mapper.toResponseDto(entity)).thenReturn(testProduct);

        // Calls the service method under test
        ProductResponseDTO result = service.getProductById(id).orElseThrow();

        // Verify the service returns the mapped DTO
        assertEquals("Comedy DVD", result.productName());
        assertEquals(id, result.id());

        // Verify repository was called
        verify(repository).findById(id);

        // Verify mapper was used to convert the entity
        verify(mapper).toResponseDto(entity);
    }

    /**
     * Testing service logic for createProductFromRequest. Verify that createProductFromRequest calls the repository
     * correctly, maps the request, and returns the mapped ProductResponseDTO for the test product.
     * */
    @Test
    void shouldCreateProduct_whenRequestIsValid() {
        // Arranging the test data
        Product entity = new Product();
        entity.setProductName("Comedy DVD");
        entity.setProductDesc("Funny");
        entity.setRetailPrice(new BigDecimal("10.69"));

        // Fake DTO request to create
        ProductRestRequestDTO restRequestDTO = new ProductRestRequestDTO("Comedy DVD", "Funny",
                new BigDecimal("10.69"));

        // Fake DTO returned by the mapper
        ProductResponseDTO testProduct = new ProductResponseDTO(1L, "Comedy DVD", "Funny",
                new BigDecimal("10.69"));

        BigDecimal STNDRD_RETAIL_MARKUP = BigDecimal.TEN; // For testing vendor price validation

        // The mock behavior -> mapper converts DTO -> entity
        when(mapper.toProduct(restRequestDTO)).thenReturn(entity);

        // The mock behavior -> repository saves the entity
        when(repository.save(entity)).thenReturn(entity);

        // The mock behavior -> mapper converts entity -> DTO
        when(mapper.toResponseDto(entity)).thenReturn(testProduct);

        // Calls the service method under test
        ProductResponseDTO result = service.createProductFromRequest(restRequestDTO);

        // Assert the service returns the mapped DTO
        assertEquals("Comedy DVD", result.productName());
        assertEquals(1L, result.id());
        assertEquals( new BigDecimal("10.69").subtract(STNDRD_RETAIL_MARKUP), entity.getVendorPrice() );

        // Verify repository was called
        verify(repository).save(entity);

        // Verify mapper was used to convert the entity
        verify(mapper).toProduct(restRequestDTO);
        verify(mapper).toResponseDto(entity);
    }
}
