package com.gestion.restaurant.dto.ingredients;

import com.gestion.restaurant.entity.ingredients.Ingredients;
import lombok.Data;

@Data
public class IngredientStockDTO {
    private Ingredients ingredient;
    private Double quantiteActuelle;

    public IngredientStockDTO(Ingredients ingredient, Double quantiteActuelle) {
        this.ingredient = ingredient;
        this.quantiteActuelle = quantiteActuelle;
    }
}