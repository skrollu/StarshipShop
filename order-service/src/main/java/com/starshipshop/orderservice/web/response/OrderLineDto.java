package com.starshipshop.orderservice.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineDto {

    @NotBlank
    @Size(min = 3)
    private String skuCode;
    @NotNull
    private BigDecimal price;
    @NotNull
    private int quantity;

}