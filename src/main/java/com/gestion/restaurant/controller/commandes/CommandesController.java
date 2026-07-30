package com.gestion.restaurant.controller.commandes;

import com.gestion.restaurant.entity.commandes.Commandes;
import com.gestion.restaurant.repository.clients.ClientsRepository;
import com.gestion.restaurant.repository.commandes.CommandesRepository;
import com.gestion.restaurant.repository.livraisons.ZonesLivraisonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/commandes")
public class CommandesController {

    private final CommandesRepository commandesRepository;
    private final ClientsRepository clientsRepository;
    private final ZonesLivraisonRepository zonesLivraisonRepository;

    public CommandesController(CommandesRepository commandesRepository, 
                               ClientsRepository clientsRepository, 
                               ZonesLivraisonRepository zonesLivraisonRepository) {
        this.commandesRepository = commandesRepository;
        this.clientsRepository = clientsRepository;
        this.zonesLivraisonRepository = zonesLivraisonRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("commandesList", commandesRepository.findAll());
        return "commandes/list";
    }

    @GetMapping("/new")
    public String showCreate(Model model) {
        model.addAttribute("commande", new Commandes());
        model.addAttribute("clients", clientsRepository.findAll());
        model.addAttribute("zones", zonesLivraisonRepository.findAll());
        return "commandes/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("commande") Commandes commande) {
        commandesRepository.save(commande);
        return "redirect:/commandes";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable("id") Long id, Model model) {
        Commandes c = commandesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Commande introuvable : " + id));
        model.addAttribute("commande", c);
        model.addAttribute("clients", clientsRepository.findAll());
        model.addAttribute("zones", zonesLivraisonRepository.findAll());
        return "commandes/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        commandesRepository.deleteById(id);
        return "redirect:/commandes";
    }
}