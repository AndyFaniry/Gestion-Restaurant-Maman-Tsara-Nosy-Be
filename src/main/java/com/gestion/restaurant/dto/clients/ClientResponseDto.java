package com.gestion.restaurant.dto.clients;

import lombok.Data;

@Data
public class ClientResponseDto {
    private Long id;
    private String nom;
    private String prenom;
    private String contact;
    private Long idTypeClient;
    private String typeClientLibelle;
}