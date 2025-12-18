/**
 * Project: Spring Practice
 * Author: Benjamin Soto-Roberts
 * Description: A simple web controller that handles a get request by returning a simple message.
 **/


package org.bsr.springsecurity.practice.authenticatingldapapplication.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {


    @GetMapping("/")
    public String index() {
        return "<h1>Welcome to the home page!</h1>";
    }
}
