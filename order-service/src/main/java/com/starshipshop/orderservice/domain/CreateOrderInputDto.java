package com.starshipshop.orderservice.domain;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderInputDto {

    @NotEmpty
    private Set<OrderLineDto> orderLines;

}
