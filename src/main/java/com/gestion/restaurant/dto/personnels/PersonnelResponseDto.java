package com.gestion.restaurant.dto.personnels;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PersonnelResponseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String contact;
    private Long idRole;
    private String roleLibelle;
    private LocalDate dateEmbauche;
    private BigDecimal salaireBase;
}