package com.gestion.restaurant.dto.recette;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RecetteIngredientFormDto {
    private Long idPlat;
    private Long idIngredient;
    private BigDecimal quantiteRequise;
}