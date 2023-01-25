package com.starshipshop.orderservice.repository.jpa.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order", updatable = false, columnDefinition = "BIGINT")
    private Long id;

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
    private Set<OrderLine> orderLines;
}
