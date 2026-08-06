package com.gestion.restaurant.dto.clients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientRequestDto {

    private Long id;

    @NotBlank(message = "Le nom du client est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    private String nom;

    @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
    private String prenom;

    @Size(max = 50, message = "Le contact ne doit pas dépasser 50 caractères")
    private String contact;

    @NotNull(message = "Le type de client est obligatoire")
    private Long idTypeClient;
}
