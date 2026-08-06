package com.gestion.restaurant.integration;

import com.gestion.restaurant.entity.materielles.Materielles;
import com.gestion.restaurant.repository.caisse.MouvementCaisseRepository;
import com.gestion.restaurant.service.materielles.MateriellesService;
import com.gestion.restaurant.support.AbstractPostgresIT;
import com.gestion.restaurant.support.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class MateriellesIT extends AbstractPostgresIT {

    @Autowired MateriellesService materiellesService;
    @Autowired TestDataFactory factory;
    @Autowired MouvementCaisseRepository mouvementCaisseRepository;

    @Test
    void achatMaintenanceHorsService() {
        factory.ensureLookups();
        var cat = factory.categorieMateriel("Cuisine");
        var statut = factory.statutMateriel("En service");
        Materielles mat = factory.materiel("Four", cat, statut);
        long mvtAvant = mouvementCaisseRepository.count();

        materiellesService.enregistrerAchat(mat.getId(), LocalDate.now(),
                new BigDecimal("2"), new BigDecimal("100000"), null);
        assertThat(materiellesService.getStockActuel(mat.getId())).isEqualByComparingTo("2");
        assertThat(mouvementCaisseRepository.count()).isEqualTo(mvtAvant + 1);

        materiellesService.enregistrerMaintenance(mat.getId(), LocalDate.now(),
                "Révision", new BigDecimal("20000"), "Tech");
        assertThat(materiellesService.findById(mat.getId()).getStatutMaterielles().getLibelle())
                .isEqualTo("En maintenance");
        assertThat(mouvementCaisseRepository.count()).isEqualTo(mvtAvant + 2);

        materiellesService.mettreHorsService(mat.getId());
        assertThat(materiellesService.findById(mat.getId()).getStatutMaterielles().getLibelle())
                .isEqualTo("Hors Service");
    }
}
