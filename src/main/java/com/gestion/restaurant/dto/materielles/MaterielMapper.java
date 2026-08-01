package com.gestion.restaurant.dto.materielles;

import com.gestion.restaurant.entity.materielles.Materielles;

public class MaterielMapper {

    public static MaterielResponseDto toDto(Materielles entity) {
        if (entity == null) return null;
        MaterielResponseDto dto = new MaterielResponseDto();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setDateEntree(entity.getDateEntree());
        if (entity.getCategorieMaterielles() != null) {
            dto.setCategorieLibelle(entity.getCategorieMaterielles().getLibelle());
            dto.setIdCategorie(entity.getCategorieMaterielles().getId());
        }
        if (entity.getStatutMaterielles() != null) {
            dto.setStatutLibelle(entity.getStatutMaterielles().getLibelle());
            dto.setIdStatut(entity.getStatutMaterielles().getId());
        }
        return dto;
    }
}