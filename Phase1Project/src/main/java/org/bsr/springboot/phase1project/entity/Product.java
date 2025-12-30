package org.bsr.springboot.phase1project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

@Entity
public class Product {

    @Id // Assigns variable to id or database pk
    @GeneratedValue(strategy = GenerationType.AUTO) // Springboot picks the generation type
    private Integer id;

    @Column(unique = true) // Products must be unique
    private String name;

    private String description;

    @Column(nullable = false, precision = 12, scale = 2) // Column constraint - cannot be null, 12 max digits and 2 dec
    @DecimalMin(value = "0.00", inclusive = true) // Min value is 0.00 inclusive
    private BigDecimal price;

    protected Product() {}

    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ProductName: " + this.getName() + " ProductDesc: " + this.getDescription() + " ProductPrice: "
                + this.getPrice();
    }
}
