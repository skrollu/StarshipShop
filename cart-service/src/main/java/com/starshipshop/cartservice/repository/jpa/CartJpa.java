package com.starshipshop.cartservice.repository.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "cart")
public class CartJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart", updatable = false, columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "id_user", updatable = false)
    private String userId;

    @Column(name = "state")
    private String state;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    private List<ProductJpa> products;
}
