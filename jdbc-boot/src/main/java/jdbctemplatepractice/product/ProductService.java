/**
 * Project: JDBCTemplate Practice
 * Description: Service class owns the business logic and coordinates the flow of data between the repo and the mapper.
 *              For all instances where product may not be found, repository propagates a custom exception so service
 *              can operate as such.
 * Author: Benjamin Soto-Roberts
 * Created: 03/13/2026
 */

package jdbctemplatepractice.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)  // safe default for reads
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public List<ProductResponseDTO> getAllProducts() {
        return repository.findAllProducts().stream().map(mapper::toResponse).toList();
    }

    public ProductResponseDTO getProductById(UUID uuid) {
        return mapper.toResponse(repository.findProductById(uuid));
    }

    @Transactional
    public ProductResponseDTO postProduct(ProductRequestDTO requestDTO) {
        Product p = mapper.toProduct(requestDTO);
        p.setProductId(UUID.randomUUID());
        p.setRetailPrice(calculateRetailPrice(p.getVendorPrice()));

        Product response = repository.saveProduct(p);
        return mapper.toResponse(response);
    }

    @Transactional
    public ProductResponseDTO putProduct(UUID uuid, ProductRequestDTO requestDTO) {
        Product p = mapper.toProduct(requestDTO);
        p.setProductId(uuid);
        p.setRetailPrice(calculateRetailPrice(p.getVendorPrice()));


        Product response = repository.updateProduct(p);
        return mapper.toResponse(response);
    }

    @Transactional
    public void deleteProductById(UUID uuid) {
        repository.deleteProductById(uuid);
    }

    private BigDecimal calculateRetailPrice(BigDecimal vendorPrice) {
        return vendorPrice.multiply(new BigDecimal("1.25"));
    }


}


