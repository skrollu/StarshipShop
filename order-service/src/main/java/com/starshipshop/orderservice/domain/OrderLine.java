package com.starshipshop.orderservice.domain;

import lombok.*;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {

    @NotBlank
    @Size(min = 3)
    private String skuCode;
    @NotNull
    private BigDecimal price;
    @NotNull
    private int quantity;

    private OrderStatus status;

    public static OrderLine create(@NotBlank @NonNull String skuCode, @NonNull BigDecimal price, int quantity) throws IllegalArgumentException {
        if (StringUtils.isBlank(skuCode))
            throw new IllegalArgumentException("Cannot create OrderLine with a blank skuCode");
        return OrderLine.builder()
                .skuCode(skuCode)
                .price(price)
                .quantity(quantity)
                .status(OrderStatus.PENDING)
                .build();
    }
}