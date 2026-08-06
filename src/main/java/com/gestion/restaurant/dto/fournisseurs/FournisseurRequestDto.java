package com.gestion.restaurant.dto.fournisseurs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FournisseurRequestDto {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
    private String prenom;

    @NotBlank(message = "Le contact est obligatoire")
    @Size(max = 50, message = "Le contact ne doit pas dépasser 50 caractères")
    private String contact;

    @NotNull(message = "Le type de fournisseur est obligatoire")
    private Long idTypeFournisseur;
}
