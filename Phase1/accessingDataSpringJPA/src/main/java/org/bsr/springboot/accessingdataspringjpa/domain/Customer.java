/******************************************************************************
 * Project: SpringBoot Data Access Guide Series
 * Description: A simple entity to model a Customer.
 * Author: Benjamin Soto-Roberts
 * Created: 2025-12-20
 * Source: https://spring.io/guides/gs/accessing-data-jpa
 ******************************************************************************/

package org.bsr.springboot.accessingdataspringjpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/*
* Entities in JPA are nothing but POJOs representing data that can be persisted in the database. An entity represents a
* table stored in a database. Every instance of an entity represents a row in the table.*/
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    protected Customer() {

    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }


}
