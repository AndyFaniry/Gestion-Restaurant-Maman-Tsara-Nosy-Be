package com.gestion.restaurant.integration;

import com.gestion.restaurant.dto.recette.RecetteRequestDto;
import com.gestion.restaurant.entity.ingredients.Ingredients;
import com.gestion.restaurant.entity.plats.Plats;
import com.gestion.restaurant.service.recette.RecetteService;
import com.gestion.restaurant.support.AbstractPostgresIT;
import com.gestion.restaurant.support.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class RecetteIT extends AbstractPostgresIT {

    @Autowired RecetteService recetteService;
    @Autowired TestDataFactory factory;

    @Test
    void ajouterEtSupprimerLigne() {
        factory.ensureLookups();
        var typeF = factory.typeFournisseur("T");
        var f = factory.fournisseur("F", typeF);
        var unite = factory.unite("u-" + System.nanoTime(), "g");
        var catI = factory.categorieIngredient("C");
        var st = factory.statutIngredient("A");
        Ingredients ing = factory.ingredient("Sel", catI, st, f, unite);
        var catP = factory.categoriePlat("Plat");
        Plats plat = factory.plat("Soupe", new BigDecimal("5000"), catP);

        RecetteRequestDto dto = new RecetteRequestDto();
        dto.setIdPlat(plat.getId());
        dto.setIdIngredient(ing.getId());
        dto.setQuantiteRequise(0.5);
        recetteService.ajouterIngredientARecette(dto);

        var lignes = recetteService.getIngredientsParPlat(plat.getId());
        assertThat(lignes).hasSize(1);

        recetteService.supprimerIngredientDeRecette(lignes.getFirst().getId());
        assertThat(recetteService.getIngredientsParPlat(plat.getId())).isEmpty();
    }
}
