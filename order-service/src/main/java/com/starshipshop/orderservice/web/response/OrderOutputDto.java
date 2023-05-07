package com.starshipshop.orderservice.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderOutputDto {
    private Long id;
    private LocalDateTime orderDate;
    private LocalDateTime sendingDate;
    private LocalDateTime cancellationDate;
    private LocalDateTime returnDate;
    private BigDecimal price;
    private HashMap<String, OrderLineOutputDto> orderLines;
}

