package com.gestion.restaurant.dto.ingredients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class IngredientRequestDto {

    private Long id;

    @NotBlank(message = "Le nom de l'ingrédient est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;

    @NotNull(message = "La catégorie est obligatoire")
    private Long idCategorie;

    @NotNull(message = "Le statut est obligatoire")
    private Long idStatut;

    @NotNull(message = "Le fournisseur est obligatoire")
    private Long idFournisseur;

    @NotNull(message = "L'unité est obligatoire")
    private Long idUnite;
}
