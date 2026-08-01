package com.gestion.restaurant.dto.recette;

import lombok.Data;

@Data
public class RecetteRequestDto {
    private Long idPlat;
    private Long idIngredient;
    private Double quantiteRequise;
}