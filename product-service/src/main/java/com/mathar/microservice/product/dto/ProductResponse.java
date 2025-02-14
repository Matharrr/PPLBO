package com.mathar.microservice.product.dto;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, String descripiton, BigDecimal price) {
  
}