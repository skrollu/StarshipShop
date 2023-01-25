package com.starshipshop.orderservice.domain;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private String orderNumber;
    private LocalDate orderDate;
    private LocalDate sendingDate;
    private LocalDate cancellationDate;
    private LocalDate returnDate;
    private List<OrderLineDto> orderLines;
}
