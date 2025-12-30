package org.bsr.springboot.ph1practice.repository;

import org.bsr.springboot.ph1practice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
