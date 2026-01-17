/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: Global exception handler for REST API error responses. Converts exceptions into proper HTTP status
 *              codes with structured error messages. Handles validation errors from @Valid annotations and business
 *              logic violations from service layer.
 * Author: Benjamin Soto-Roberts
 * Created: 01/16/26
 * */

package org.bsr.springboot.foundations.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for all REST controllers. Intercepts exceptions and converts them to appropriate HTTP
 * responses. @RestControllerAdvice applies to all @RestController classes
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors from @Valid annotations on DTOs. Triggered when request body fails validation
     * constraints like @NotBlank, @DecimalMin, @Size, etc. Returns 400 Bad Request with field-level error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        // Extract field errors from the binding result
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        // Build structured error response
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Failed");
        response.put("errors", fieldErrors);

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handles business logic violations from the service layer. Triggered when ProductService throws
     * IllegalArgumentException for business rule violations (e.g., retail price below minimum threshold). Returns 400
     * Bad Request with error message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handles database constraint violations (e.g., unique constraint on product name). Triggered when attempting to
     * save a product with a duplicate name. Returns 409 Conflict with appropriate message.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Conflict");

        // Check if it's a unique constraint violation on product name
        String message = ex.getMessage();
        if (message != null && message.toUpperCase().contains("PRODUCT_NAME")) {
            response.put("message", "A product with this name already exists");
        } else {
            response.put("message", "Database constraint violation");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}