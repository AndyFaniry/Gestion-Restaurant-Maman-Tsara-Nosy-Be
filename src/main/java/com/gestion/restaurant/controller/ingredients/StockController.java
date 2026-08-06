package com.gestion.restaurant.controller.ingredients;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Alias de navigation : le stock se gère via les achats/sorties d'ingrédients.
 * Conserve l'URL historique {@code /stocks} pour la sidebar.
 */
@Controller
@RequestMapping("/stocks")
public class StockController {

    @GetMapping
    public String redirectToStockGlobal() {
        return "redirect:/ingredients/stock";
    }
}
