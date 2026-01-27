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
import org.bsr.springboot.foundations.presentation.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

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
        ProductResponseDTO dto = new ProductResponseDTO(
                1L,
                "Comedy DVD",
                "Funny",
                new BigDecimal("1.69")
        );

        // The mock behavior -> repository returns list containing the entity
        when(repository.findAllByProductNameContainingIgnoreCase("dvd")).thenReturn(List.of(entity));

        // The mock behavior -> mapper converts entity → DTO
        when(mapper.toResponseDto(entity)).thenReturn(dto);

        // Calls the service method under test
        List<ProductResponseDTO> results = service.getProductsContaining(request);

        // Assert: verify the service returns the mapped DTO list
        assertEquals(1, results.size(), "Expected exactly one result");
        assertEquals("DVD Player", results.getFirst().productName());

        // Verify repository was called with the trimmed search term
        verify(repository).findAllByProductNameContainingIgnoreCase("dvd");
        verify(mapper).toResponseDto(entity); // Verify mapper was used to convert the entity
    }

}
