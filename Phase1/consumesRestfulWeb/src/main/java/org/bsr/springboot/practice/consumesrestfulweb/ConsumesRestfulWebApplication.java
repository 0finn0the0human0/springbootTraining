/******************************************************************************
 * Project: SpringBoot RestAPI Guide Series
 * Description: An application that consumes a RESTful web service

 * Author: Benjamin Soto-Roberts
 * Created: 2025-12-15
 * Source: https://spring.io/guides/gs/consuming-rest
 ******************************************************************************/

package org.bsr.springboot.practice.consumesrestfulweb;

import org.bsr.springboot.practice.consumesrestfulweb.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@SpringBootApplication
public class ConsumesRestfulWebApplication {

    // Creates a a static logger
    final static Logger LOGGER = LoggerFactory.getLogger(ConsumesRestfulWebApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConsumesRestfulWebApplication.class, args);
    }


    /**
     * ApplicationRunner is a functional interface that runs after the application context is initialized. Uses Spring’s
     * RestClient.Builder to create a RestClient with a base URL pointing to the local server to make HTTP requests.
     * The lambda args -> executes at startup and calls the rest client, deserializes the response body into a Quote
     * object, and logs the quote using a static logger*/
    @Bean
    @Profile("!test") // --> Ensures does not run during tests
    public ApplicationRunner run(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl("https://localhost:8080").build();
        return args -> {
            Quote quote = restClient.get().uri("/api/random").retrieve().body(Quote.class);
            LOGGER.info(Objects.requireNonNull(quote).toString());
        };
    }
}
