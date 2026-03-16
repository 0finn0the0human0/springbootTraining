/**
 * Project: JDBCTemplate Practice
 * Description: Tests the ProductService class and methods
 * Author: Benjamin Soto-Roberts
 * Created: 03/13/2026
 */


package jdbctemplatepractice.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;




}
