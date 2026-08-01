package com.gestion.restaurant.dto.materielles;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MaterielRequestDto {
    private Long id;
    private String nom;
    private LocalDate dateEntree;
    private Long idCategorie;
    private Long idStatut;
}