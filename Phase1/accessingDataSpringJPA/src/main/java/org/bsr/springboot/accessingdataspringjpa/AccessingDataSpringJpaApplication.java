/******************************************************************************
 * Project: SpringBoot Data Access Guide Series
 * Description: An application that uses Spring Data JPA to store and retrieve data in a relational database.
 * Author: Benjamin Soto-Roberts
 * Created: 2025-12-20
 * Source: https://spring.io/guides/gs/accessing-data-jpa
 ******************************************************************************/

package org.bsr.springboot.accessingdataspringjpa;

import org.bsr.springboot.accessingdataspringjpa.domain.Customer;
import org.bsr.springboot.accessingdataspringjpa.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessingDataSpringJpaApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessingDataSpringJpaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataSpringJpaApplication.class, args);
    }


    /**
     * This CommandLineRunner executes a demo routine immediately after the application starts. defaults to an in‑memory
     * H2 database since H2 is included on the classpath and no datasource properties configurations are needed.
     * */
    @Bean
    public CommandLineRunner demo(CustomerRepository custRepo) {
        return args -> {
            // Adds customer objects to the database
            custRepo.save(new Customer("Brian", "Stewart"));
            custRepo.save(new Customer("Stanly", "Stewart"));
            custRepo.save(new Customer("Sam", "Stewart"));
            custRepo.save(new Customer("Camantha", "Stewart"));
            custRepo.save(new Customer("Briana", "Stewart"));

            LOGGER.info("======Customers found with find all=======");
            custRepo.findAll().forEach(customer -> LOGGER.info(customer.toString()));

            LOGGER.info("======Find By ID example=======");
            LOGGER.info(custRepo.findById(3).toString());

            LOGGER.info("======Find By Last Name example=======");
            custRepo.findByLastName("Stewart").forEach(customer -> LOGGER.info(customer.toString()));



        };

    }

}
