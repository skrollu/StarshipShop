package com.starshipshop.orderservice.repository.jpa;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "order_line")
public class OrderLineJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_line", updatable = false, columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "sku_code", updatable = false)
    private String skuCode;

    @Column(name = "price", updatable = false)
    private BigDecimal price;

    @Column(name = "quantity", updatable = false)
    private int quantity;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order", nullable = false, foreignKey = @ForeignKey(name = "FK_order_line_order"))
    private OrderJpa order;

}
