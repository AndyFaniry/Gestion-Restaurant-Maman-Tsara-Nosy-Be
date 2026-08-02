package com.gestion.restaurant.dto.commandes;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CommandeLigneRequestDto {
    private Long idPlat;
    private BigDecimal quantite;
}