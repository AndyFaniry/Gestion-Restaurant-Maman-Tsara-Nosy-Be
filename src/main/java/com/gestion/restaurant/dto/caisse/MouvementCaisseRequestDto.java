package com.gestion.restaurant.dto.caisse;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MouvementCaisseRequestDto {

    private Long id;

    @NotNull(message = "La date du mouvement est obligatoire")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateMouvement;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.001", message = "Le montant doit être strictement positif")
    private BigDecimal montant;

    @NotNull(message = "Le type de mouvement est obligatoire")
    private Long idTypeMouvement;
}
