package com.starshipshop.orderservice.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
public class OrderLine {

    @NotBlank
    @Size(min = 3)
    private String skuCode;
    @NotNull
    private BigDecimal price;
    @NotNull
    private int quantity;

    private OrderStatus status;

    public OrderLine (@NotBlank @NonNull String skuCode, @NonNull BigDecimal price, int quantity) {
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
        this.status = OrderStatus.PENDING;
    }
}