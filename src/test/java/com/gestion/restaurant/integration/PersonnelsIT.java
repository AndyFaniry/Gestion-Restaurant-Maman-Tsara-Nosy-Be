package com.gestion.restaurant.integration;

import com.gestion.restaurant.entity.personnels.FichePaie;
import com.gestion.restaurant.entity.personnels.Personnels;
import com.gestion.restaurant.repository.caisse.MouvementCaisseRepository;
import com.gestion.restaurant.service.personnels.PersonnelsService;
import com.gestion.restaurant.support.AbstractPostgresIT;
import com.gestion.restaurant.support.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class PersonnelsIT extends AbstractPostgresIT {

    @Autowired PersonnelsService personnelsService;
    @Autowired TestDataFactory factory;
    @Autowired MouvementCaisseRepository mouvementCaisseRepository;

    @Test
    void fichePaieEtAbsence() {
        factory.ensureLookups();
        var role = factory.role("Cuisinier");
        Personnels p = factory.personnel("Andry", role);
        var raison = factory.raisonAbsence("Maladie");
        long mvtAvant = mouvementCaisseRepository.count();

        FichePaie fp = personnelsService.genererFichePaie(p.getId(),
                new BigDecimal("400000"), new BigDecimal("50000"), LocalDate.now());
        assertThat(fp.getMontantTotal()).isEqualByComparingTo("450000");
        assertThat(mouvementCaisseRepository.count()).isEqualTo(mvtAvant + 1);
        assertThat(personnelsService.findHistoriquePaie(p.getId())).hasSize(1);

        personnelsService.enregistrerAbsence(p.getId(),
                LocalDate.now(), LocalDate.now().plusDays(2), raison.getId(), "repos");
        assertThat(personnelsService.findAbsences(p.getId())).hasSize(1);
    }
}
