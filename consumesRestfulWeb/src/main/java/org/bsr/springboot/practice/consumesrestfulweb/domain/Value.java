/******************************************************************************
 * Project: SpringBoot RestAPI Guide Series
 * Description: A domain record to embed the inner quotation itself to the Quote Record.

 * Author: Benjamin Soto-Roberts
 * Created: 2025-12-15
 * Source: https://spring.io/guides/gs/consuming-rest
 ******************************************************************************/

package org.bsr.springboot.practice.consumesrestfulweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Any properties not bound in this type should be ignored
@JsonIgnoreProperties(ignoreUnknown = true)
public record Value(Long id, String quote) {
}
