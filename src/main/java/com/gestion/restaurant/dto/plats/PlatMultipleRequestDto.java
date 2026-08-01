package com.gestion.restaurant.dto.plats;

import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PlatMultipleRequestDto {

    @Data
    public static class IngredientQuantiteDto {
        private Long idIngredient;
        private BigDecimal quantiteRequise;
    }

    @Data
    public static class PlatFormItem {
        private String nom;
        private Long idCategorie;
        private BigDecimal prixVente;
        private List<IngredientQuantiteDto> ingredients = new ArrayList<>();
    }

    private List<PlatFormItem> plats = new ArrayList<>();
}