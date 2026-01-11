/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The FoundationsApplication class runs the SpringBoot application and seeds the database after the
 *              application context.
 * Author: Benjamin Soto-Roberts
 * Created: 01/02/26
 * */

package org.bsr.springboot.foundations;

import org.bsr.springboot.foundations.persistence.entity.Product;
import org.bsr.springboot.foundations.persistence.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;


@SpringBootApplication
public class FoundationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoundationsApplication.class, args);
    }



    /**
     * The method uses the CommandLineRunner to seed the database after the application context runs. Uses the
     * ProductRepository to save products to the BD
     * */
    @Bean
    public CommandLineRunner demo(ProductRepository repository) {
        return args -> {

            repository.save(new Product("Adventure Time DVD", "The Best DVD in stores!",
                    new BigDecimal("19.99"), new BigDecimal("9.99")));
            repository.save(new Product("Steven Universe DVD", "The 2nd Best DVD in stores!",
                    new BigDecimal("17.99"), new BigDecimal("7.99")));
            repository.save(new Product("Meshuggah CD", "The Best CD in stores!",
                    new BigDecimal("17.99"), new BigDecimal("7.99")));
            repository.save(new Product("Jack Stauber CD", "The 2nd Best CD in stores!",
                    new BigDecimal("17.99"), new BigDecimal("7.99")));

        };
    }
}
