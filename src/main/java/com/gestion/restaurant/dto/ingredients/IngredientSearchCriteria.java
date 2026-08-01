package com.gestion.restaurant.dto.ingredients;

import lombok.Data;

@Data
public class IngredientSearchCriteria {
    private String nom;
    private Long idCategorie;
    private Long idStatut;
}