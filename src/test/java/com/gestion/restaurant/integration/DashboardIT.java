package com.gestion.restaurant.integration;

import com.gestion.restaurant.service.dashboard.DashboardService;
import com.gestion.restaurant.support.AbstractPostgresIT;
import com.gestion.restaurant.support.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class DashboardIT extends AbstractPostgresIT {

    @Autowired DashboardService dashboardService;
    @Autowired TestDataFactory factory;

    @Test
    void getStats_apresSeed() {
        factory.ensureLookups();
        factory.client("Dash", factory.typeClient("Std"));
        factory.personnel("Emp", factory.role("Serveur"));
        var typeF = factory.typeFournisseur("F");
        var f = factory.fournisseur("Fx", typeF);
        var unite = factory.unite("u-" + System.nanoTime(), "g");
        factory.ingredient("Ing", factory.categorieIngredient("C"), factory.statutIngredient("A"), f, unite);

        DashboardService.DashboardStats stats = dashboardService.getStats();
        assertThat(stats.totalClients()).isGreaterThanOrEqualTo(1);
        assertThat(stats.totalPersonnels()).isGreaterThanOrEqualTo(1);
        assertThat(stats.totalIngredients()).isGreaterThanOrEqualTo(1);
        assertThat(stats.totalCommandes()).isGreaterThanOrEqualTo(0);
    }
}
