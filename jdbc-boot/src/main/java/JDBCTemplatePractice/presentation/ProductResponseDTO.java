package JDBCTemplatePractice.presentation;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(UUID uuid,
                                 String productName,
                                 String productDesc,
                                 BigDecimal retailPrice,
                                 BigDecimal vendorPrice) {
}

