package com.gestion.restaurant.service.livraisons;

import com.gestion.restaurant.dto.livraisons.ZoneLivraisonDto;
import com.gestion.restaurant.entity.livraisons.ZonesLivraison;
import com.gestion.restaurant.exception.BusinessRuleException;
import com.gestion.restaurant.exception.ResourceNotFoundException;
import com.gestion.restaurant.repository.livraisons.ZoneLivraisonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ZoneLivraisonServiceTest {

    @Mock ZoneLivraisonRepository repository;
    @InjectMocks ZoneLivraisonService service;

    @Test
    void save_libelleUnique_creation() {
        when(repository.existsByLibelleIgnoreCase("Centre")).thenReturn(true);
        ZoneLivraisonDto dto = zoneDto(null, "Centre", "0", "5", "1000");
        assertThatThrownBy(() -> service.save(dto))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("libellé");
    }

    @Test
    void save_minSuperieurMax() {
        ZoneLivraisonDto dto = zoneDto(null, "Nord", "10", "5", "1000");
        assertThatThrownBy(() -> service.save(dto))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("minimale");
    }

    @Test
    void save_ok() {
        when(repository.existsByLibelleIgnoreCase("Sud")).thenReturn(false);
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        service.save(zoneDto(null, "Sud", "0", "8", "2500"));
        verify(repository).save(any(ZonesLivraison.class));
    }

    @Test
    void save_update_libelleConflit() {
        when(repository.existsByLibelleIgnoreCaseAndIdNot("Autre", 1L)).thenReturn(true);
        assertThatThrownBy(() -> service.save(zoneDto(1L, "Autre", "0", "1", "100")))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    void findById_introuvable() {
        when(repository.findById(9L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(9L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void toDto() {
        ZonesLivraison z = new ZonesLivraison();
        z.setId(1L);
        z.setLibelle("Z");
        z.setMin(BigDecimal.ONE);
        z.setMax(BigDecimal.TEN);
        z.setPrix(new BigDecimal("100"));
        assertThat(service.toDto(z).getLibelle()).isEqualTo("Z");
    }

    private static ZoneLivraisonDto zoneDto(Long id, String libelle, String min, String max, String prix) {
        ZoneLivraisonDto dto = new ZoneLivraisonDto();
        dto.setId(id);
        dto.setLibelle(libelle);
        dto.setMin(new BigDecimal(min));
        dto.setMax(new BigDecimal(max));
        dto.setPrix(new BigDecimal(prix));
        return dto;
    }
}
