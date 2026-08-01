package com.gestion.restaurant.dto.ingredients;

import com.gestion.restaurant.entity.ingredients.Ingredients;

public class IngredientMapper {

    public static IngredientResponseDto toDto(Ingredients entity) {
        if (entity == null) return null;
        
        IngredientResponseDto dto = new IngredientResponseDto();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        
        if (entity.getCategorieIngredients() != null) {
            dto.setCategorieLibelle(entity.getCategorieIngredients().getLibelle());
            dto.setIdCategorie(entity.getCategorieIngredients().getId());
        }
        if (entity.getStatutIngredient() != null) {
            dto.setStatutLibelle(entity.getStatutIngredient().getLibelle());
            dto.setIdStatut(entity.getStatutIngredient().getId());
        }
        if (entity.getFournisseur() != null) {
            dto.setFournisseurNom(entity.getFournisseur().getNom() + " " + entity.getFournisseur().getPrenom());
            dto.setIdFournisseur(entity.getFournisseur().getId()); // Add this mapping
        }
        if (entity.getUnite() != null) {
            dto.setUniteNom(entity.getUnite().getNom());
            dto.setUniteSymbole(entity.getUnite().getSymbole());
            dto.setIdUnite(entity.getUnite().getId());            // Add this mapping
        }
        return dto;
    }
}