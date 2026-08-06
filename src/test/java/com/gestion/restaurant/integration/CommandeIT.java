package com.gestion.restaurant.integration;

import com.gestion.restaurant.dto.commandes.CommandeCreateRequestDto;
import com.gestion.restaurant.dto.commandes.CommandeLigneRequestDto;
import com.gestion.restaurant.entity.commandes.Commandes;
import com.gestion.restaurant.exception.BusinessRuleException;
import com.gestion.restaurant.repository.caisse.MouvementCaisseRepository;
import com.gestion.restaurant.repository.commandes.CommandesRepository;
import com.gestion.restaurant.repository.commandes.DetailsCommandesRepository;
import com.gestion.restaurant.repository.commandes.FacturesCommandesRepository;
import com.gestion.restaurant.service.commandes.CommandesService;
import com.gestion.restaurant.service.ingredients.IngredientsService;
import com.gestion.restaurant.support.AbstractPostgresIT;
import com.gestion.restaurant.support.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class CommandeIT extends AbstractPostgresIT {

    @Autowired CommandesService commandesService;
    @Autowired IngredientsService ingredientsService;
    @Autowired TestDataFactory factory;
    @Autowired CommandesRepository commandesRepository;
    @Autowired DetailsCommandesRepository detailsCommandesRepository;
    @Autowired FacturesCommandesRepository facturesCommandesRepository;
    @Autowired MouvementCaisseRepository mouvementCaisseRepository;

    @Test
    void creerPuisSupprimer_impacteStockEtCaisse() {
        var scenario = factory.commandeScenario();
        BigDecimal stockAvant1 = ingredientsService.getStockActuel(scenario.ingredient1().getId());
        BigDecimal stockAvant2 = ingredientsService.getStockActuel(scenario.ingredient2().getId());
        long mvtAvant = mouvementCaisseRepository.count();

        CommandeCreateRequestDto dto = new CommandeCreateRequestDto();
        dto.setIdClient(scenario.client().getId());
        dto.setIdZoneLivraison(scenario.zone().getId());
        dto.setDateCommande(LocalDate.now());
        CommandeLigneRequestDto ligne = new CommandeLigneRequestDto();
        ligne.setIdPlat(scenario.plat().getId());
        ligne.setQuantite(new BigDecimal("2"));
        dto.setLignes(List.of(ligne));

        Commandes commande = commandesService.creerCommande(dto);

        // zone 2000 + plat 15000*2 = 32000 ; destock 2*2 et 3*2
        assertThat(commande.getMontantTotal()).isEqualByComparingTo("32000");
        assertThat(detailsCommandesRepository.findByCommandeId(commande.getId())).hasSize(1);
        assertThat(facturesCommandesRepository.count()).isGreaterThan(0);
        assertThat(ingredientsService.getStockActuel(scenario.ingredient1().getId()))
                .isEqualByComparingTo(stockAvant1.subtract(new BigDecimal("4")));
        assertThat(ingredientsService.getStockActuel(scenario.ingredient2().getId()))
                .isEqualByComparingTo(stockAvant2.subtract(new BigDecimal("6")));
        assertThat(mouvementCaisseRepository.count()).isEqualTo(mvtAvant + 1);

        commandesService.deleteById(commande.getId());

        assertThat(commandesRepository.findById(commande.getId())).isEmpty();
        assertThat(ingredientsService.getStockActuel(scenario.ingredient1().getId()))
                .isEqualByComparingTo(stockAvant1);
        assertThat(ingredientsService.getStockActuel(scenario.ingredient2().getId()))
                .isEqualByComparingTo(stockAvant2);
        assertThat(mouvementCaisseRepository.count()).isEqualTo(mvtAvant + 2);
    }

    /**
     * Propagation NOT_SUPPORTED : la transaction du service doit rollback toute seule.
     * Avec {@code @Transactional} sur la classe de test, une commande flushée resterait
     * visible même après exception (le test catch l'erreur puis committerait le cadre).
     */
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void creer_stockInsuffisant_aucuneCommande() {
        var scenario = factory.commandeScenario();
        ingredientsService.enregistrerSortieOuPerte(
                scenario.ingredient1().getId(),
                ingredientsService.getStockActuel(scenario.ingredient1().getId()),
                IngredientsService.MVT_SORTIE_CUISINE,
                LocalDate.now());

        long countAvant = commandesRepository.count();
        CommandeCreateRequestDto dto = new CommandeCreateRequestDto();
        dto.setIdClient(scenario.client().getId());
        dto.setIdZoneLivraison(scenario.zone().getId());
        CommandeLigneRequestDto ligne = new CommandeLigneRequestDto();
        ligne.setIdPlat(scenario.plat().getId());
        ligne.setQuantite(BigDecimal.ONE);
        dto.setLignes(List.of(ligne));

        assertThatThrownBy(() -> commandesService.creerCommande(dto))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("Stock insuffisant");
        assertThat(commandesRepository.count()).isEqualTo(countAvant);
    }
}
