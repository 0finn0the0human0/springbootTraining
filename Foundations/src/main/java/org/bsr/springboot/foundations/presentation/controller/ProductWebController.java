/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ProductWebController handles incoming web requests for the form-based UI. It exposes GET and POST
 *              endpoints, binds form data to DTOs, performs validation, and delegates business logic to ProductService.
 * Author: Benjamin Soto-Roberts
 * Created: 01/03/26
 * */

package org.bsr.springboot.foundations.presentation.controller;

import jakarta.validation.Valid;
import org.bsr.springboot.foundations.presentation.dto.ProductRequestDTO;
import org.bsr.springboot.foundations.presentation.dto.ProductResponseDTO;
import org.bsr.springboot.foundations.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductWebController {

    // Final for thread safety
    private final ProductService productService;

    /*The ProductService is injected into the constructor. SpringBoot automatically injects dependencies into a class
     with a single constructor*/
    public ProductWebController(ProductService productService) {
        this.productService = productService;
    }


    /**
     * The method that handles the Get requests to the root url. Renders the empty form when the user first loads the
     * page.
     * */
    @GetMapping("/")
    public String showForm(Model model) {
        // A new ProductRequestDTO("") is added to the model so Thymeleaf can bind form fields to it.
        model.addAttribute("productNameQuery", new ProductRequestDTO(""));
        return "form";
    }

    /**
     * The method that handles the Get requests to the root url. Processes the submitted form, validates input, and
     * displays results. @Valid triggers Jakarta Validation on ProductRequestDTO fields. @ModelAttribute binds form
     * fields to the DTO instance. BindingResult contains the validation errors if any that are bound to the dto field.
     * If validation fails, the form is returned again, if valid, ProductService calls the method to return products
     * containing the search text from the client input and binds it to the model to return in the results url.
     * */
    @PostMapping("/")
    public String checkProductName(@Valid @ModelAttribute("productNameQuery")ProductRequestDTO productNameQuery,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        // Maps products returned from the productService class into response dtos
        List<ProductResponseDTO> products = productService.getProductsContaining(productNameQuery);
        // Binds products to the model to return in the results html.
        model.addAttribute("products", products);

        return "results";

    }
}
