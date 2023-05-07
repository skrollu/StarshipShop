package com.starshipshop.orderservice.domain;

import lombok.*;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    private Long id;
    private String userId;
    private LocalDateTime orderDate;
    private LocalDateTime sendingDate;
    private LocalDateTime cancellationDate;
    private LocalDateTime returnDate;
    private BigDecimal price;
    private HashMap<String, OrderLine> orderLines;
    private OrderStatus status;

    public static Order create(@NotEmpty @NonNull String userId, @NotEmpty @NonNull HashMap<String, OrderLine> orderLines) throws IllegalArgumentException {
        if (StringUtils.isBlank(userId)) throw new IllegalArgumentException("Cannot create order with a blank userId");
        if (orderLines.isEmpty()) throw new IllegalArgumentException("Cannot create order without orderLines");
        Order result = Order.builder()
                .userId(userId)
                .orderDate(LocalDateTime.now())
                .orderLines(orderLines)
                .status(OrderStatus.PENDING)
                .build();

        BigDecimal orderPrice = new BigDecimal(0);
        for (Map.Entry<String, OrderLine> entry : orderLines.entrySet())
            orderPrice = orderPrice.add(entry.getValue().getPrice()).multiply(BigDecimal.valueOf(entry.getValue().getQuantity()));
        result.setPrice(orderPrice);

        return result;
    }
}
