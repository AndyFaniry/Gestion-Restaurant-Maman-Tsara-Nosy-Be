package com.gestion.restaurant.entity.ingredients;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "etatstockingredient")
@Data
@NoArgsConstructor
public class EtatStockIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idingredient", nullable = false)
    private Ingredients ingredient;

    @Column(name = "dateetatstock", nullable = false)
    private LocalDate dateEtatStock;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal quantite = BigDecimal.ZERO;

    public EtatStockIngredient(Ingredients ingredient, LocalDate dateEtatStock, BigDecimal quantite) {
        this.ingredient = ingredient;
        this.dateEtatStock = dateEtatStock;
        this.quantite = quantite;
    }
}