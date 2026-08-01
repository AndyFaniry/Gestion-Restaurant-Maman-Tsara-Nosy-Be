package com.gestion.restaurant.dto.ingredients;

import lombok.Data;

@Data
public class IngredientRequestDto {
    private Long id;
    private String nom;
    private Long idCategorie;
    private Long idStatut;
    private Long idFournisseur;
    private Long idUnite;
}