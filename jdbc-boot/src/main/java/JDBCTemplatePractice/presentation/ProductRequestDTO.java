package JDBCTemplatePractice.presentation;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank @Size(max = 255) String productName,

        @Size(max = 1000) String productDesc,

        @NotNull @DecimalMin("0.01") @Digits(integer = 7, fraction = 2) BigDecimal vendorPrice

) {
}
