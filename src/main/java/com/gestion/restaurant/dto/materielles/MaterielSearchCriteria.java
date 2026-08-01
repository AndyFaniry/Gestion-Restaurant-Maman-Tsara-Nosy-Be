package com.gestion.restaurant.dto.materielles;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class MaterielSearchCriteria {
    private String nom;
    private Long idCategorie;
    private Long idStatut;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEntreeDebut;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEntreeFin;
}