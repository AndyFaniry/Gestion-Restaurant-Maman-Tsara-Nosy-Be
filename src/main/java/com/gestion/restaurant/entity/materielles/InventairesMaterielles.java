package com.gestion.restaurant.entity.materielles;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "inventairesmaterielles")
@Data
@NoArgsConstructor
public class InventairesMaterielles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idmaterielles", nullable = false)
    private Materielles materiel;

    @Column(name = "dateinventaire", nullable = false)
    private LocalDate dateInventaire;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal quantite;

    @ManyToOne
    @JoinColumn(name = "typemvtmaterielles", nullable = false)
    private TypeMvtMaterielles typeMvtMaterielles;
}