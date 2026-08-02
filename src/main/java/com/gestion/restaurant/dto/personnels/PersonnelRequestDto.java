package com.gestion.restaurant.dto.personnels;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PersonnelRequestDto {
    private Long id;
    private String nom;
    private String prenom;
    private String contact;
    private Long idRole;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEmbauche;
    
    private BigDecimal salaireBase;
}