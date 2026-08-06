package com.gestion.restaurant.dto.commandes;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CommandeLigneRequestDto {

    @NotNull(message = "Le plat est obligatoire")
    private Long idPlat;

    @NotNull(message = "La quantité est obligatoire")
    @DecimalMin(value = "0.001", message = "La quantité doit être strictement positive")
    private BigDecimal quantite;
}
