/***********************************************************************************************************************
 * Project: Phase 1: Core Spring Boot Foundations
 * Description: A practice CRUD application with REST endpoints and database integration.
 * Author: Benjamin Soto-Roberts
 * Created: 12/25/2025
 ***************************************************************/

package org.bsr.springboot.ph1practice;

import org.bsr.springboot.ph1practice.entity.Product;
import org.bsr.springboot.ph1practice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class Ph1PracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ph1PracticeApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo(ProductRepository repository) {
        return args -> {
          repository.save(new Product("A DVD", new BigDecimal("19.99")));
          repository.save(new Product("AB DVD", new BigDecimal("19.99")));
          repository.save(new Product("C DVD", new BigDecimal("19.99")));
        };
    }
}
