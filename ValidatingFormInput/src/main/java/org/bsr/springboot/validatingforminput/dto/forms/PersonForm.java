/******************************************************************************
 * Project: SpringBoot IO Guide Series
 * Description: A class that backs the form used to create a person involved in validating a user’s name and age, so you
 * first need to create
 * Author: Benjamin Soto-Roberts
 * Created: 2025-12-21
 * Source: https://spring.io/guides/gs/validating-form-input
 ******************************************************************************/

package org.bsr.springboot.validatingforminput.dto.forms;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PersonForm {

    // Enforces no null input for name and that the size of the input is between 2 and 30 characters
    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    // Enforces no null input for age and that the minimum acceptable age is 18
    @NotNull
    @Min(18)
    private Integer age;

    @Override
    public String toString() {
        return "PersonForm{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
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
}
