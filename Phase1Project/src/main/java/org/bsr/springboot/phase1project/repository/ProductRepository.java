package org.bsr.springboot.phase1project.repository;

import org.bsr.springboot.phase1project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
