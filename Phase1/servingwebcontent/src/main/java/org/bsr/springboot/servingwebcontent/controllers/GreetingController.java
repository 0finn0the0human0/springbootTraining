/******************************************************************************
 * Project: SpringBoot Web Guide Series
 * Description: A simple Spring Boot application that consumes a RESTful web service
 *              and demonstrates server-side rendering with Thymeleaf.
 * Author: Benjamin Soto-Roberts
 * Created: 2025-12-18
 * Source: https://spring.io/guides/gs/serving-web-content
 ******************************************************************************/

package org.bsr.springboot.servingwebcontent.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * GreetingController handles HTTP GET requests to the /greeting endpoint.
 * It returns the logical view name ("greeting"), which is resolved by Spring MVC
 * to render the corresponding HTML template.
 */
@Controller
public class GreetingController {

    /**
     * Handles GET requests to /greeting by using the @GetMapping annotation to map the method to the /greeting url.
     * The @RequestParam binds the query parameter "name" to the method argument if a name is provided, otherwise the
     * default is "World". The name (provided or default) is added to the Model which makes it available to the view
     * template for rendering dynamic content.
     **/
    @GetMapping("/greeting/")
    public String greeting(@RequestParam(name="name", required = false, defaultValue = "World")
                           String name, Model model) {

        // Add the "name" attribute to the model so the view can access it
        model.addAttribute("name", name);

        // Return the logical view name "greeting"
        // Spring will resolve this to a template file (e.g., greeting.html)
        return "greeting";
    }

    @GetMapping("/fairwell/")
    public String fairWell(@RequestParam(name="samename", required = false, defaultValue = "Sucker")
                               String name, Model model) {
        model.addAttribute("samename", name);


        return "fairwell";
    }
}

