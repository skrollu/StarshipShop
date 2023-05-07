package com.starshipshop.orderservice.web.request;

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
public class OrderLineInputDto {
    @NotBlank
    @Size(min = 3)
    private String skuCode;
    @NotNull
    private BigDecimal price;
    @NotNull
    private int quantity;
}
