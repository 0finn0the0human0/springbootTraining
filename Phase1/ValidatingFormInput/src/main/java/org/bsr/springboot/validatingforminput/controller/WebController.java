/******************************************************************************
 * Project: SpringBoot IO Guide Series
 * Description: A controller class that defines callback methods to customize the Java-based configuration for Spring
 * MVC. Renders form and controls redirect on successful validation.
 * Author: Benjamin Soto-Roberts
 * Created: 2025-12-21
 * Source: https://spring.io/guides/gs/validating-form-input
 ******************************************************************************/

package org.bsr.springboot.validatingforminput.controller;

import jakarta.validation.Valid;

import org.bsr.springboot.validatingforminput.dto.forms.PersonForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Controller
public class WebController implements WebMvcConfigurer {

    /**
     * Registers a simple view controller that maps the "/results" URL directly to the "results" view template. This
     * avoids creating a dedicated controller method for pages that require no processing or model data.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }


    /**
     * Adds a PersonForm instance to the model under the name "personForm" so the form.html template can bind to it
     * when rendering the form.
     */
    @GetMapping("/")
    public String showForm(@ModelAttribute("personForm") PersonForm personForm) {
        return "form";
    }


    /**
     * Processes the submitted PersonForm. The @Valid annotation triggers Bean Validation on the bound form object, and
     * any validation errors are captured in the BindingResult. If errors are present, the form view is re-rendered so
     * the user can correct the input. On successful validation, the user is redirected to the results page.
     */
    @PostMapping("/")
    public String checkPersonInfo(@Valid @ModelAttribute("personForm") PersonForm personForm,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        return "redirect:/results";
    }
}