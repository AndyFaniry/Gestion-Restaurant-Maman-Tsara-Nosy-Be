package com.gestion.restaurant.dto.ingredients;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
@Data
public class AchatIngredientRequestDto {
    private Long idIngredient;
    private Double quantite;
    private Double prixAchatUnitaire;
    private LocalDate dateEntree;
    private LocalDate datePeremption;
}