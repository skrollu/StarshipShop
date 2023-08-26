package com.starshipshop.productservice.repository.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "starship_product")
public class StarshipProductJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product", updatable = false, columnDefinition = "BIGINT")
    private Long id;
    @Column(name = "id_starship", nullable = false)
    private Long starshipId;
    @Column(name = "sku_code", nullable = false)
    private String skuCode;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "color")
    private String color;
}
