/**
 * Project: JDBCTemplate Practice
 * Description: Tests the ProductService class and methods
 * Author: Benjamin Soto-Roberts
 * Created: 03/13/2026
 */


package jdbctemplatepractice.product;

import jdbctemplatepractice.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTests {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;


    @Test
    void shouldCalculateRetail_whenCreateProduct() {
        ProductRequestDTO requestDTO = new ProductRequestDTO("Test Product",
                "No Description...",
                new BigDecimal("15.00"));

        Product bareEntity = new Product();
        bareEntity.setVendorPrice(new BigDecimal("15.00"));

        when(mapper.toProduct(requestDTO)).thenReturn(bareEntity);
        when(repository.saveProduct(any(Product.class))).thenReturn(bareEntity);

        service.postProduct(requestDTO);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).saveProduct(captor.capture());

        assertThat(captor.getValue().getRetailPrice()).isEqualByComparingTo(new BigDecimal("18.75"));
    }

    @Test
    void shouldAssignUUID_whenCreateProduct() {
        ProductRequestDTO requestDTO = new ProductRequestDTO("Test Product", "No Description...", new BigDecimal("15.00"));

        Product bareEntity = new Product();
        bareEntity.setVendorPrice(new BigDecimal("15.00"));

        when(mapper.toProduct(requestDTO)).thenReturn(bareEntity);
        when(repository.saveProduct(any(Product.class))).thenReturn(bareEntity);

        service.postProduct(requestDTO);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).saveProduct(captor.capture());

        assertThat(captor.getValue().getUuid()).isNotNull();
    }

    @Test
    void shouldPropagateException_whenProductNotFoundById() {
        UUID uuid = UUID.randomUUID();
        when(repository.findProductById(uuid)).thenThrow(new ProductNotFoundException("Product not found: " + uuid));

        assertThatThrownBy(() -> service.getProductById(uuid))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(uuid.toString());
    }

    @Test
    void shouldCalculateRetailPrice_whenUpdateProduct() {
        UUID uuid = UUID.randomUUID();
        ProductRequestDTO requestDTO = new ProductRequestDTO("Updated", "desc", new BigDecimal("20.00"));

        Product bareEntity = new Product();
        bareEntity.setVendorPrice(new BigDecimal("20.00"));

        when(mapper.toProduct(requestDTO)).thenReturn(bareEntity);
        when(repository.updateProduct(any(Product.class))).thenReturn(bareEntity);

        service.putProduct(uuid, requestDTO);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).updateProduct(captor.capture());

        assertThat(captor.getValue().getRetailPrice()).isEqualByComparingTo(new BigDecimal("25.00"));
    }

    @Test
    void shouldPassUUIDThrough_whenUpdateProduct() {
        UUID uuid = UUID.randomUUID();
        ProductRequestDTO requestDTO = new ProductRequestDTO("Updated", "desc", new BigDecimal("20.00"));

        Product bareEntity = new Product();
        bareEntity.setVendorPrice(new BigDecimal("20.00"));

        when(mapper.toProduct(requestDTO)).thenReturn(bareEntity);
        when(repository.updateProduct(any(Product.class))).thenReturn(bareEntity);

        service.putProduct(uuid, requestDTO);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).updateProduct(captor.capture());

        assertThat(captor.getValue().getUuid()).isEqualTo(uuid);
    }

    @Test
    void shouldPropagateException_whenUpdateProductNotFound() {
        UUID uuid = UUID.randomUUID();
        ProductRequestDTO requestDTO = new ProductRequestDTO("Updated", "desc", new BigDecimal("20.00"));

        Product bareEntity = new Product();
        bareEntity.setVendorPrice(new BigDecimal("20.00"));

        when(mapper.toProduct(requestDTO)).thenReturn(bareEntity);
        when(repository.updateProduct(any(Product.class)))
                .thenThrow(new ProductNotFoundException("Product not found: " + uuid));

        assertThatThrownBy(() -> service.putProduct(uuid, requestDTO))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(uuid.toString());
    }

    @Test
    void shouldInvokeRepository_whenDeleteProduct() {
        UUID uuid = UUID.randomUUID();

        service.deleteProductById(uuid);

        verify(repository).deleteProductById(uuid);
    }

    @Test
    void shouldPropagateException_whenDeleteProductNotFound() {
        UUID uuid = UUID.randomUUID();
        doThrow(new ProductNotFoundException("Product not found: " + uuid))
                .when(repository).deleteProductById(uuid);

        assertThatThrownBy(() -> service.deleteProductById(uuid))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining(uuid.toString());
    }



}
