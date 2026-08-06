package com.gestion.restaurant.integration;

import com.gestion.restaurant.entity.fournisseurs.Fournisseurs;
import com.gestion.restaurant.entity.ingredients.Ingredients;
import com.gestion.restaurant.repository.caisse.MouvementCaisseRepository;
import com.gestion.restaurant.service.ingredients.IngredientsService;
import com.gestion.restaurant.support.AbstractPostgresIT;
import com.gestion.restaurant.support.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class IngredientStockIT extends AbstractPostgresIT {

    @Autowired IngredientsService ingredientsService;
    @Autowired TestDataFactory factory;
    @Autowired MouvementCaisseRepository mouvementCaisseRepository;

    @Test
    void achatSortieReintegration() {
        factory.ensureLookups();
        var typeF = factory.typeFournisseur("Ali");
        Fournisseurs f = factory.fournisseur("F1", typeF);
        var unite = factory.unite("g-" + System.nanoTime(), "g");
        var cat = factory.categorieIngredient("Cat");
        var statut = factory.statutIngredient("OK");
        Ingredients ing = factory.ingredient("Huile", cat, statut, f, unite);

        long mvtAvant = mouvementCaisseRepository.count();
        LocalDate today = LocalDate.now();

        ingredientsService.enregistrerAchatEntree(ing.getId(), today, today.plusDays(30),
                new BigDecimal("20"), new BigDecimal("500"));
        assertThat(ingredientsService.getStockActuel(ing.getId())).isEqualByComparingTo("20");
        assertThat(mouvementCaisseRepository.count()).isEqualTo(mvtAvant + 1);

        ingredientsService.enregistrerSortieOuPerte(ing.getId(), new BigDecimal("5"),
                IngredientsService.MVT_SORTIE_PERIME, today);
        assertThat(ingredientsService.getStockActuel(ing.getId())).isEqualByComparingTo("15");
        assertThat(mouvementCaisseRepository.count()).isEqualTo(mvtAvant + 1);

        ingredientsService.reintegrerStock(ing.getId(), new BigDecimal("3"), today);
        assertThat(ingredientsService.getStockActuel(ing.getId())).isEqualByComparingTo("18");
        assertThat(mouvementCaisseRepository.count()).isEqualTo(mvtAvant + 1);
        assertThat(ingredientsService.findInventaire(ing.getId())).isNotEmpty();
        assertThat(ingredientsService.findHistorique(ing.getId())).hasSize(1);
    }
}
