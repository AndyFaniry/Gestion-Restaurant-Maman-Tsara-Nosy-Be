package com.gestion.restaurant.entity.livraisons;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "zoneslivraison")
@Data
@NoArgsConstructor
public class ZonesLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String libelle;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal min;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal max;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal prix;
}
