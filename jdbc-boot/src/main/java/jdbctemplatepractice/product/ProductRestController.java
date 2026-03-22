/**
 * Project: JDBCTemplate Practice
 * Description: The ProductRestController is a REST api that exposes a REST endpoint for running CRUD operations on
 *              Product entities. Returns JSON responses with appropriate HTTP status codes.
 * Author: Benjamin Soto-Roberts
 * Created: 03/21/2026
 */

package jdbctemplatepractice.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService service;

    public ProductRestController(ProductService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable UUID uuid) {
        return ResponseEntity.ok(service.getProductById(uuid));
    }
}
