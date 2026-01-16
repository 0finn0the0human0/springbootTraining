/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ProductRestRequestDTO is a data transfer object that is used in a POST request handled by the
 *              ProductRestController.
 * Author: Benjamin Soto-Roberts
 * Created: 01/13/26
 * */

package org.bsr.springboot.foundations.presentation.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRestRequestDTO(

        @NotBlank(message = "Product Name is required.")
        @Size(min = 2, max = 100, message = "Character length 2-100.")
        @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message ="No special characters allowed.")
        String productName,

        @Size(max = 1000, message = "Max character length 1000.")
        String productDesc,

        @NotNull(message = "Retail Price is required.")
        @DecimalMin(value = "0.00", message = "Retail Price must be >= $0.00")
        @Digits(integer = 5, fraction = 2, message = "Retail Price max limit is 99999.99.")
        BigDecimal retailPrice
) {
}
