package com.gestion.restaurant.dto.materielles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MaterielRequestDto {

    private Long id;

    @NotBlank(message = "Le nom du matériel est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;

    private LocalDate dateEntree;

    @NotNull(message = "La catégorie est obligatoire")
    private Long idCategorie;

    @NotNull(message = "Le statut est obligatoire")
    private Long idStatut;
}
