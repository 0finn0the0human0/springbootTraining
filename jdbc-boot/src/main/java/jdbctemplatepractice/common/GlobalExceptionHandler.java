/**
 * Project: JDBCTemplate Practice
 * Description: A Global Exception Handler that handles exceptions that propagate up to the controller
 * Author: Benjamin Soto-Roberts
 * Created: 03/21/2026
 */

package jdbctemplatepractice.common;

import jdbctemplatepractice.product.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ProductNotFoundException and returns a 404 ProblemDetail response.
     * */
    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail handleProductNotFound(ProductNotFoundException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Product Not Found");
        pd.setType(URI.create("errors/product-not-found"));
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }

    /**
     * Handles MethodArgumentTypeMismatchException and returns a 400 ProblemDetail response when incorrect type is used
     * in path variable or request param.
     * */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String detail = "Parameter '" + ex.getName() + "' expects type " + ex.getRequiredType().getSimpleName();
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
        pd.setTitle("Invalid Parameter Type");
        pd.setType(URI.create("errors/type-mismatch"));
        pd.setProperty("timestamp", Instant.now());
        return pd;
    }
}