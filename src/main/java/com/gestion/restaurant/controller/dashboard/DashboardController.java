package com.gestion.restaurant.controller.dashboard;

import com.gestion.restaurant.service.dashboard.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/dashboard"})
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public String dashboard(Model model) {
        DashboardService.DashboardStats stats = dashboardService.getStats();
        model.addAttribute("totalCommandes", stats.totalCommandes());
        model.addAttribute("totalClients", stats.totalClients());
        model.addAttribute("totalIngredients", stats.totalIngredients());
        model.addAttribute("totalPersonnels", stats.totalPersonnels());
        return "dashboard/index";
    }
}
