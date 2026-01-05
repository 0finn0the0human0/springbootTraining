/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The Product Repository interface defines the query methods to be used on the product entity using
 *              JpaRepository. Adds an abstract method to return products containing a search term within the product
 *              name field.
 * Author: Benjamin Soto-Roberts
 * Created: 01/02/26
 * */

package org.bsr.springboot.foundations.persistence.repository;

import org.bsr.springboot.foundations.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * An abstract method to find products containing a search text that represents text that can be found in the
     * product name. The method returns a List of Products
     * */
    List<Product> findAllByProductNameContainingIgnoreCase(String productName);
}
