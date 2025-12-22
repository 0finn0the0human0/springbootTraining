/******************************************************************************
 * Project: SpringBoot IO Guide Series
 * Description: A simple Spring MVC application that takes user input and checks the input by using standard validation
 * annotations
 * Author: Benjamin Soto-Roberts
 * Created: 2025-12-21
 * Source: https://spring.io/guides/gs/validating-form-input
 ******************************************************************************/

package org.bsr.springboot.validatingforminput;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ValidatingFormInputApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidatingFormInputApplication.class, args);
    }

}
