package com.gestion.restaurant.service.dashboard;

import com.gestion.restaurant.repository.clients.ClientsRepository;
import com.gestion.restaurant.repository.commandes.CommandesRepository;
import com.gestion.restaurant.repository.ingredients.IngredientsRepository;
import com.gestion.restaurant.repository.personnels.PersonnelsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock CommandesRepository commandesRepository;
    @Mock ClientsRepository clientsRepository;
    @Mock IngredientsRepository ingredientsRepository;
    @Mock PersonnelsRepository personnelsRepository;
    @InjectMocks DashboardService service;

    @Test
    void getStats() {
        when(commandesRepository.count()).thenReturn(3L);
        when(clientsRepository.count()).thenReturn(5L);
        when(ingredientsRepository.count()).thenReturn(7L);
        when(personnelsRepository.count()).thenReturn(2L);

        DashboardService.DashboardStats stats = service.getStats();
        assertThat(stats.totalCommandes()).isEqualTo(3L);
        assertThat(stats.totalClients()).isEqualTo(5L);
        assertThat(stats.totalIngredients()).isEqualTo(7L);
        assertThat(stats.totalPersonnels()).isEqualTo(2L);
    }
}
