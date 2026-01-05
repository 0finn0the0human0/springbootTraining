/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ProductResponseDTO is a data transfer object meant to display the results of client requests.
 * Author: Benjamin Soto-Roberts
 * Created: 01/02/26
 * */

package org.bsr.springboot.foundations.presentation.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String productName,
        String productDesc,
        BigDecimal retailPrice
) {
}
