/**
 * Project: JDBCTemplate Practice
 * Description: Request DTO used for validating and transferring product data submitted by api clients.
 * Author: Benjamin Soto-Roberts
 * Created: 03/09/2026
 */

package jdbctemplatepractice.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank @Size(max = 255) String productName,

        @Size(max = 1000) String productDesc,

        @NotNull @DecimalMin("0.01") @Digits(integer = 7, fraction = 2) BigDecimal vendorPrice) {}
