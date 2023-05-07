package com.starshipshop.orderservice.repository.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "orders")
public class OrderJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order", updatable = false, columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "id_user", updatable = false)
    private String userId;

    @Column(name = "order_date", updatable = false)
    @NotNull(message = "Order date cannot be null")
    private LocalDateTime orderDate;

    @Column(name = "sending_date", updatable = false)
    private LocalDateTime sendingDate;

    @Column(name = "cancellation_date", updatable = false)
    private LocalDateTime cancellationDate;

    @Column(name = "return_date", updatable = false)
    private LocalDateTime returnDate;

    @Column(name = "price", updatable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<OrderLineJpa> orderLines;
}
