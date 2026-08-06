package com.gestion.restaurant.integration;

import com.gestion.restaurant.dto.livraisons.ZoneLivraisonDto;
import com.gestion.restaurant.exception.BusinessRuleException;
import com.gestion.restaurant.service.livraisons.ZoneLivraisonService;
import com.gestion.restaurant.support.AbstractPostgresIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class ZoneLivraisonIT extends AbstractPostgresIT {

    @Autowired ZoneLivraisonService service;

    @Test
    void uniciteLibelle() {
        ZoneLivraisonDto dto = new ZoneLivraisonDto();
        dto.setLibelle("Ambatoloaka");
        dto.setMin(BigDecimal.ZERO);
        dto.setMax(new BigDecimal("5"));
        dto.setPrix(new BigDecimal("3000"));
        service.save(dto);

        ZoneLivraisonDto doublon = new ZoneLivraisonDto();
        doublon.setLibelle("ambatoloaka");
        doublon.setMin(BigDecimal.ZERO);
        doublon.setMax(new BigDecimal("3"));
        doublon.setPrix(new BigDecimal("2000"));

        assertThatThrownBy(() -> service.save(doublon))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("libellé");
    }
}
