package JDBCTemplatePractice.presentation;

import JDBCTemplatePractice.persistence.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toProduct(ProductRequestDTO requestDTO) {
        Product p = new Product();

        return p;
    }
}
