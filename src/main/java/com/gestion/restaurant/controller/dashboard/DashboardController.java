package com.gestion.restaurant.controller.dashboard;

import com.gestion.restaurant.repository.commandes.CommandesRepository;
import com.gestion.restaurant.repository.clients.ClientsRepository;
import com.gestion.restaurant.repository.ingredients.IngredientsRepository;
import com.gestion.restaurant.repository.personnels.PersonnelsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/dashboard"})
public class DashboardController {

    private final CommandesRepository commandesRepository;
    private final ClientsRepository clientsRepository;
    private final IngredientsRepository ingredientsRepository;
    private final PersonnelsRepository personnelsRepository;

    public DashboardController(CommandesRepository commandesRepository,
                               ClientsRepository clientsRepository,
                               IngredientsRepository ingredientsRepository,
                               PersonnelsRepository personnelsRepository) {
        this.commandesRepository = commandesRepository;
        this.clientsRepository = clientsRepository;
        this.ingredientsRepository = ingredientsRepository;
        this.personnelsRepository = personnelsRepository;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalCommandes", commandesRepository.count());
        model.addAttribute("totalClients", clientsRepository.count());
        model.addAttribute("totalIngredients", ingredientsRepository.count());
        model.addAttribute("totalPersonnels", personnelsRepository.count());

        return "dashboard";
    }
}