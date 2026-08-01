package com.gestion.restaurant.entity.materielles;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "etatstockmaterielles")
@Data
@NoArgsConstructor
public class EtatStockMaterielles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idmaterielles", nullable = false)
    private Materielles materiel;

    @Column(name = "dateetatstock", nullable = false)
    private LocalDate dateEtatStock;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal quantite;
}