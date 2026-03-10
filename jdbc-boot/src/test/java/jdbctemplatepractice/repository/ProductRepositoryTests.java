/**
 * Project: JDBCTemplate Practice
 * Description: A Test class that tests the implementation of the
 * Author: Benjamin Soto-Roberts
 * Created: 03/03/2026
 */


package jdbctemplatepractice.repository;

import jdbctemplatepractice.persistence.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import jdbctemplatepractice.persistence.Product;
import jdbctemplatepractice.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ProductRepositoryTests {

    // -- Data field
    @Autowired
    private ProductRepository productRepository;


    /**
     * Tests for valid retrieval of product by id
     */
    @Test
    void shouldReturnProduct_whenValidUUID() {
        // Arrange the test data
        UUID request = UUID.fromString("7193e96f-5c3a-4231-bbf0-0f2388d654ad");
        Product response = productRepository.getProductById(request);

        // Checks test data request results
        assertThat(response).isNotNull();
        assertThat(response.getProductName()).isEqualTo("Adventure Time DVD");

        System.out.println(response); // Display data to console for verification

    }

    /**
     * Tests response for invalid UUID request, ensures proper exception is thrown from repository
     */
    @Test
    void shouldThrowException_whenUUIDNotFound() {
        // Arrange the test data - Unknown uuid
        UUID request = UUID.fromString("3f44afd4-cb79-4fd3-b57d-c06a02c710c5");

        // Checks response results
        assertThatThrownBy(() -> productRepository.getProductById(request))
                .isInstanceOf(ProductNotFoundException.class);
    }

    /**
     * Tests for valid retrieval of all products
     */
    @Test
    void shouldReturnAllProducts() {
        // Arrange test data
        List<Product> response = productRepository.getAllProducts();

        // Checks response results
        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(2);

        response.forEach(System.out::println); // Display data to console for verification
    }

    /**
     * Tests a valid save request to ensure the product is saved and is retrievable from the db. Dirties context to not
     * impact other tests
     */
    @Test
    @DirtiesContext
    void shouldSaveProduct_whenRequestIsValid() {
        // Arrange the test data
        Product request = new Product();
        request.setUuid(UUID.randomUUID());
        request.setProductName("Test Product");
        request.setProductDesc("Test Description...");
        request.setRetailPrice(new BigDecimal("19.99"));
        request.setVendorPrice(new BigDecimal("12.99"));

        // Sends the request to db to return a valid product response object after save request
        Product response = productRepository.saveProduct(request);

        // Checks response results
        assertThat(response.getProductName()).isEqualTo("Test Product");
        System.out.println(response); // Display data to console for verification
    }

    /**
     * Tests a valid save request to ensure the product is saved and is retrievable from the db. Dirties context to not
     * impact other tests
     */
    @Test
    void shouldUpdateProduct_whenRequestIsValid() {
        // Arrange the test data
        Product request = new Product();
        request.setUuid(UUID.fromString("4f8b6f8e-92ba-4c2d-ba98-cb0c442176f7"));
        request.setProductName("Steven Universe DVD");
        request.setProductDesc("The 2nd best dvd in the world");
        request.setRetailPrice(new BigDecimal("15.99"));
        request.setVendorPrice(new BigDecimal("10.99"));


        // Sends the request to db to return a valid product response object after save request
        Product response = productRepository.updateProduct(request);

        // Checks response results
        assertThat(response.getProductName()).isEqualTo("Steven Universe DVD");
        System.out.println(response); // Display data to console for verification
    }

    @Test
    @DirtiesContext
    void shouldDeleteProduct_whenRequestIsValid() {
        // Arrange the test data
        UUID request = UUID.fromString("7193e96f-5c3a-4231-bbf0-0f2388d654ad");

        // Sends request to db to delete, returns void
        productRepository.deleteProductById(request);

        // Checks response results
        assertThatThrownBy(() -> productRepository.getProductById(request))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
