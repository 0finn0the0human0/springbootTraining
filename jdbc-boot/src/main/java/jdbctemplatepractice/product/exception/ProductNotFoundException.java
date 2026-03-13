/**
 * Project: JDBCTemplate Practice
 * Description: Domain-specific exception indicating that a requested Product could not be found. This is the unified
 *              "not found" signal used by the repository/service layer.
 * Author: Benjamin Soto-Roberts
 * Created: 03/04/2026
 */

package jdbctemplatepractice.product.exception;


public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
