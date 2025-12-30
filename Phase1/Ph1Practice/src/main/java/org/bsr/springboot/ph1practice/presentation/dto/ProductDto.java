package org.bsr.springboot.ph1practice.presentation.dto;

import java.math.BigDecimal;

public record ProductDto(String name, String description, BigDecimal price) {
}
