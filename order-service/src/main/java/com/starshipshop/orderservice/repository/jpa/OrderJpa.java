package com.starshipshop.orderservice.repository.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Column(name = "order_number", updatable = false)
    private String orderNumber;

    @Column(name = "order_date", updatable = false)
    @NotNull(message = "Order date cannot be null")
    private LocalDate orderDate;

    @Column(name = "sending_date", updatable = false)
    private LocalDate sendingDate;

    @Column(name = "cancellation_date", updatable = false)
    private LocalDate cancellationDate;

    @Column(name = "return_date", updatable = false)
    private LocalDate returnDate;

    @Column(name = "price", updatable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<OrderLineJpa> orderLines;
}
