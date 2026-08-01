package com.gestion.restaurant.dto.ingredients;

import lombok.Data;

@Data
public class IngredientResponseDto {
    private Long id;
    private String nom;
    private String categorieLibelle;
    private Long idCategorie;
    private String statutLibelle;
    private Long idStatut;
    private String fournisseurNom;
    private Long idFournisseur; //
    private String uniteNom;
    private String uniteSymbole;
    private Long idUnite;//
}