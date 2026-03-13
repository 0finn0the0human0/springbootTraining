/**
 * Project: JDBCTemplate Practice
 * Description: A simple mapper that maps request data to products and products to response dto's.
 * Author: Benjamin Soto-Roberts
 * Created: 03/09/2026
 */

package jdbctemplatepractice.product;

import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toProduct(ProductRequestDTO requestDTO) {
        Product product = new Product();
        product.setProductName(requestDTO.productName());
        product.setProductDesc(requestDTO.productDesc());
        product.setVendorPrice(requestDTO.vendorPrice());
        return product;
    }

    public ProductResponseDTO toResponse(Product product) {
        return new ProductResponseDTO(product.getUuid(),
                product.getProductName(),
                product.getProductDesc(),
                product.getRetailPrice(),
                product.getVendorPrice());

    }
}
