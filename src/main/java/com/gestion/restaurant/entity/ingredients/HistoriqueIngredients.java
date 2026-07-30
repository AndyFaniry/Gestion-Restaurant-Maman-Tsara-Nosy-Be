package com.gestion.restaurant.entity.ingredients;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "historiqueingredients")
@Data
@NoArgsConstructor
public class HistoriqueIngredients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idingredient", nullable = false)
    private Ingredients ingredient;

    @Column(name = "dateentree", nullable = false)
    private LocalDate dateEntree;

    @Column(name = "dateperemption")
    private LocalDate datePeremption;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal quantite;

    @Column(name = "prixachat", nullable = false, precision = 16, scale = 3)
    private BigDecimal prixAchat;
}
