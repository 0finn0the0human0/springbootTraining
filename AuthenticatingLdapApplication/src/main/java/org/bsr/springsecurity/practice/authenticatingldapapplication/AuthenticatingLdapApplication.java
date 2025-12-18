/**
 * Project: Spring Practice
 * Author: Benjamin Soto-Roberts
 * Description: Launches the application with SpringBoot convenience annotation enabling default springboot
 * configuration.
 **/


package org.bsr.springsecurity.practice.authenticatingldapapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AuthenticatingLdapApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticatingLdapApplication.class, args);
    }

}
