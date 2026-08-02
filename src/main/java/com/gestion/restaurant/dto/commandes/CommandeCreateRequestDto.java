package com.gestion.restaurant.dto.commandes;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommandeCreateRequestDto {
    private Long id;
    private Long idClient;
    private Long idZoneLivraison;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateCommande;

    private List<CommandeLigneRequestDto> lignes = new ArrayList<>();
}