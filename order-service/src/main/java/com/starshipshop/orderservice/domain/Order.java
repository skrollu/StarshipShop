package com.starshipshop.orderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    private Long id;
    private String userId;
    private String orderNumber;
    private LocalDate orderDate;
    private LocalDate sendingDate;
    private LocalDate cancellationDate;
    private LocalDate returnDate;
    private BigDecimal price;
    private HashMap<String, OrderLine> orderLines;
}
