package com.starshipshop.orderservice.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderInputDto {

    @NotEmpty
    private Set<OrderLineInputDto> orderLines;

}
