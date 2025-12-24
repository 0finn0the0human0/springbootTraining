/******************************************************************************
 * Project: SpringBoot IO Guide Series
 * Description: A class that backs the form used to create a person involved in validating a user’s name and age.
 * Author: Benjamin Soto-Roberts
 * Created: 2025-12-21
 * Source: https://spring.io/guides/gs/validating-form-input
 ******************************************************************************/

package org.bsr.springboot.validatingforminput.dto.forms;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PersonForm {

    @NotNull
    @Size(min=2, max=30)
    private String name;

    @NotNull
    @Min(18)
    private Integer age;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String toString() {
        return "Person(Name: " + this.name + ", Age: " + this.age + ")";
    }
}