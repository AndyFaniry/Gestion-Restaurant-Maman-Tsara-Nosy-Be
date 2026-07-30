package com.gestion.restaurant.entity.commandes;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "facturescommandes")
@Data
@NoArgsConstructor
public class FacturesCommandes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idcommande", nullable = false)
    private Commandes commande;

    @Column(name = "datefacture", nullable = false)
    private LocalDate dateFacture;

    @Column(name = "montanttotal", nullable = false, precision = 16, scale = 3)
    private BigDecimal montantTotal;
}
