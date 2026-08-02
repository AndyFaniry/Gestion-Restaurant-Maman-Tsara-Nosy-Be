package com.gestion.restaurant.dto.personnels;

import com.gestion.restaurant.entity.personnels.Personnels;

public class PersonnelMapper {
    public static PersonnelResponseDto toDto(Personnels entity) {
        if (entity == null) return null;
        PersonnelResponseDto dto = new PersonnelResponseDto();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        dto.setContact(entity.getContact());
        dto.setDateEmbauche(entity.getDateEmbauche());
        if (entity.getRolePersonnels() != null) {
            dto.setIdRole(entity.getRolePersonnels().getId());
            dto.setRoleLibelle(entity.getRolePersonnels().getLibelle());
        }
        return dto;
    }
}