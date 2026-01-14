/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The Product class is an entity that maps to a table in a database. The Product entity has an id that is
 *              auto generated and is the PK, a product name that has column constraints enforcing uniqueness, non-
 *              nullability, and a max length of 255 characters, a product description that has a column constraint of
 *              a maximum of 1000 characters and by default can contain null values, and a retail and vendor price both
 *              with column constraints of a min decimal value or 0.00 and having a precision of 7 and scale of 2 for
 *              monetary values.
 * Author: Benjamin Soto-Roberts
 * Created: 01/02/26
 * */

package org.bsr.springboot.foundations.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false, length = 255)
    private String productName;

    @Column(length = 1000)
    private String productDesc;

    @Column(precision = 7, scale = 2, nullable = false)
    @DecimalMin("0.00")
    private BigDecimal retailPrice;

    @Column(precision = 7, scale = 2, nullable = false)
    @DecimalMin("0.00")
    private BigDecimal vendorPrice;

    public Product() {}

    public Product(String productName, String productDesc, BigDecimal retailPrice, BigDecimal vendorPrice) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.retailPrice = retailPrice;
        this.vendorPrice = vendorPrice;
    }

    public Product(String productName, String productDesc, BigDecimal retailPrice) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.retailPrice = retailPrice;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public BigDecimal getVendorPrice() {
        return vendorPrice;
    }

    public void setVendorPrice(BigDecimal vendorPrice) {
        this.vendorPrice = vendorPrice;
    }
}
