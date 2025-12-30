package org.bsr.springboot.phase1project;

import org.bsr.springboot.phase1project.entity.Product;
import org.bsr.springboot.phase1project.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class Phase1ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(Phase1ProjectApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo(ProductRepository repository) {
        return args -> {
            repository.save(new Product("Adventure Time DVD", "The Best DVD!",
                  new BigDecimal("19.99")));

            repository.save(new Product("Steven Universe DVD", "The 2nd Best DVD!",
                  new BigDecimal("17.99")));

            repository.save(new Product("Batman DVD", "The 3rd Best DVD!",
                  new BigDecimal("15.99")));
        };
    }
}