/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ProductRequestDTO is a data transfer object meant to capture client requests that map to the product
 *              name field in the product table/entity. The record uses JPA validation constraints to handle user inputs
 *              that are blank, contains less than the min or more than the max length of characters and also uses a
 *              regex pattern to ensure that only letters (upper and lower), numbers, hyphens, and spaces are allowed in
 *              the input.
 * Author: Benjamin Soto-Roberts
 * Created: 01/02/26
 * */

package org.bsr.springboot.foundations.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductRequestDTO (

    @NotBlank(message = "Enter search text.")
    @Size(min = 2, max = 255, message = "Invalid character length, Please try again.")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "An invalid character was entered, Please try again.")
    String productName

)
{}
