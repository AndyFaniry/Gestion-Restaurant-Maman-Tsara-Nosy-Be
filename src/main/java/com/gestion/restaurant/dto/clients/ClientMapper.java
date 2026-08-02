package com.gestion.restaurant.dto.clients;

import com.gestion.restaurant.entity.clients.Clients;

public class ClientMapper {

    public static ClientResponseDto toDto(Clients entity) {
        if (entity == null) return null;
        ClientResponseDto dto = new ClientResponseDto();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        dto.setContact(entity.getContact());
        if (entity.getTypeClient() != null) {
            dto.setIdTypeClient(entity.getTypeClient().getId());
            dto.setTypeClientLibelle(entity.getTypeClient().getLibelle());
        }
        return dto;
    }
}