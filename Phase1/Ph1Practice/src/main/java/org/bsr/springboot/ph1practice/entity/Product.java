/***********************************************************************************************************************
 * Project: Phase 1: Core Spring Boot Foundations
 * Description: A simple Product entity to store in a database and pass through a rest endpoint
 * Author: Benjamin Soto-Roberts
 * Created: 12/27/2025
 ***************************************************************/

package org.bsr.springboot.ph1practice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 10 digits and 2 decimal places")
    @DecimalMin(value = "0.00", inclusive = true, message = "Price must be positive")
    private BigDecimal price;


    protected Product() {}

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;

    }

    public Integer getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private void setPrice(BigDecimal price) {
        this.price = price;
    }
}



