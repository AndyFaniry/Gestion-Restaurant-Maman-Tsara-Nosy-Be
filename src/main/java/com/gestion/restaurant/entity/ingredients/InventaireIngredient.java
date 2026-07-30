package com.gestion.restaurant.entity.ingredients;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "inventaireingredient")
@Data
@NoArgsConstructor
public class InventaireIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idingredient", nullable = false)
    private Ingredients ingredient;

    @Column(name = "dateinventaire", nullable = false)
    private LocalDate dateInventaire;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal quantite;

    @ManyToOne
    @JoinColumn(name = "typemvtingredient", nullable = false)
    private TypeMvtIngredient typeMvtIngredient;
}
