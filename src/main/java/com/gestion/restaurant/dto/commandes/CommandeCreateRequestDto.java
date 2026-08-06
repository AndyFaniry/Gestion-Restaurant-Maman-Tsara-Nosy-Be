package com.gestion.restaurant.dto.commandes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommandeCreateRequestDto {

    private Long id;

    @NotNull(message = "Le client est obligatoire")
    private Long idClient;

    @NotNull(message = "La zone de livraison est obligatoire")
    private Long idZoneLivraison;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateCommande;

    @NotEmpty(message = "La commande doit contenir au moins un plat")
    @Valid
    private List<CommandeLigneRequestDto> lignes = new ArrayList<>();
}
