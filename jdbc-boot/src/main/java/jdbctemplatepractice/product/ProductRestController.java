/**
 * Project: JDBCTemplate Practice
 * Description: The ProductRestController is a REST api that exposes a REST endpoint for running CRUD operations on
 *              Product entities. Returns JSON responses with appropriate HTTP status codes.
 * Author: Benjamin Soto-Roberts
 * Created: 03/21/2026
 */

package jdbctemplatepractice.product;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService service;

    public ProductRestController(ProductService service) {
        this.service = service;
    }

    /**
     * Retrieves all available products, returns 200 OK with a JSON array of ProductResponseDTO objects.
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    /**
     * Retrieves a single product by its UUID. If the product does not exist, ProductNotFoundException is thrown
     * and handled by the global RestControllerAdvice, returning a 404 ProblemDetail response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    /**
     * Creates a new product and returns a 201 Created response with a Location header
     * pointing to the newly created resource. Validation is applied to the incoming request body. If validation fails,
     * Spring automatically returns a 400 Bad Request with validation details.
     */
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createNewProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {

        ProductResponseDTO createdProduct = service.postProduct(requestDTO);
        // location of the newly created resource relative to this endpoint
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() // gets the base from the request
                .path("/{id}") // add to the path
                .buildAndExpand(createdProduct.uuid()) // replaces placeholder id
                .toUri();

        return ResponseEntity.created(location).body(createdProduct);
    }

    /**
     * Updates a product and returns a 200 OK response. Validation is applied to the request body. If
     * validation fails, Spring returns a 400 Bad Request. If the product does not exist, ProductNotFoundException is
     * thrown and handled by RestControllerAdvice, returning a 404 ProblemDetail response.
     * */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable UUID id,
                                                            @Valid @RequestBody ProductRequestDTO requestDTO) {
        return ResponseEntity.ok(service.putProduct(id, requestDTO));

    }


    /**
     * Deletes a product by its UUID. If the product does not exist, ProductNotFoundException is thrown and handled
     * by RestControllerAdvice, returning a 404 ProblemDetail response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        service.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
