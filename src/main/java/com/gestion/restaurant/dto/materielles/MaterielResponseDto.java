package com.gestion.restaurant.dto.materielles;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MaterielResponseDto {
    private Long id;
    private String nom;
    private LocalDate dateEntree;
    private String categorieLibelle;
    private Long idCategorie;
    private String statutLibelle;
    private Long idStatut;
}