package com.gestion.restaurant.dto.personnels;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PersonnelRequestDto {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    private String nom;

    @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
    private String prenom;

    @Size(max = 50, message = "Le contact ne doit pas dépasser 50 caractères")
    private String contact;

    @NotNull(message = "Le rôle est obligatoire")
    private Long idRole;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEmbauche;

    @DecimalMin(value = "0.0", inclusive = true, message = "Le salaire de base doit être positif ou nul")
    private BigDecimal salaireBase;
}
