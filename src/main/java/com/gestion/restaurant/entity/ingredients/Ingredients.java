package com.gestion.restaurant.entity.ingredients;
import com.gestion.restaurant.entity.fournisseur.Fournisseurs;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingredients")
@Data
@NoArgsConstructor
public class Ingredients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "idcategorieingredients", nullable = false)
    private CategorieIngredients categorieIngredients;

    @ManyToOne
    @JoinColumn(name = "idstatutingredient", nullable = false)
    private StatutIngredient statutIngredient;

    @ManyToOne
    @JoinColumn(name = "idfournisseur", nullable = false)
    private Fournisseurs fournisseur;
}
