/**
 * Project: JDBCTemplate Practice
 * Description: Response DTO representing product data returned to api clients.
 * Author: Benjamin Soto-Roberts
 * Created: 03/09/2026
 */

package jdbctemplatepractice.presentation;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(
        UUID uuid,
        String productName,
        String productDesc,
        BigDecimal retailPrice,
        BigDecimal vendorPrice) {}

