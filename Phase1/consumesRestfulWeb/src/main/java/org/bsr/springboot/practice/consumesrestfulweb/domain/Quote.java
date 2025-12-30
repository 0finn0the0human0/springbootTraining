/******************************************************************************
 * Project: SpringBoot RestAPI Guide Series
 * Description: A domain record that models a random quotation as a json object.

 * Author: Benjamin Soto-Roberts
 * Created: 2025-12-15
 * Source: https://spring.io/guides/gs/consuming-rest
 ******************************************************************************/

package org.bsr.springboot.practice.consumesrestfulweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Any properties not bound in this type should be ignored
@JsonIgnoreProperties(ignoreUnknown = true)
public record Quote(String type, Value value) {
}
