package com.gestion.restaurant.controller.commandes;

import com.gestion.restaurant.dto.commandes.CommandeCreateRequestDto;
import com.gestion.restaurant.dto.commandes.CommandeLigneRequestDto;
import com.gestion.restaurant.entity.commandes.Commandes;
import com.gestion.restaurant.repository.clients.ClientsRepository;
import com.gestion.restaurant.repository.livraisons.ZoneLivraisonRepository;
import com.gestion.restaurant.repository.plats.PlatsRepository;
import com.gestion.restaurant.service.commandes.CommandesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/commandes")
public class CommandesController {

    private final CommandesService commandesService;
    private final ClientsRepository clientsRepository;
    private final ZoneLivraisonRepository zonesLivraisonRepository;
    private final PlatsRepository platsRepository;

    public CommandesController(CommandesService commandesService,
                               ClientsRepository clientsRepository, 
                               ZoneLivraisonRepository zonesLivraisonRepository,
                               PlatsRepository platsRepository) {
        this.commandesService = commandesService;
        this.clientsRepository = clientsRepository;
        this.zonesLivraisonRepository = zonesLivraisonRepository;
        this.platsRepository = platsRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("commandesList", commandesService.findAll());
        return "commandes/list";
    }

    @GetMapping("/new")
    public String showCreate(Model model) {
        model.addAttribute("commandeDto", new CommandeCreateRequestDto());
        model.addAttribute("clients", clientsRepository.findAll());
        model.addAttribute("zones", zonesLivraisonRepository.findAll());
        model.addAttribute("plats", platsRepository.findAll());
        return "commandes/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("commandeDto") CommandeCreateRequestDto dto) {
        commandesService.creereOuMettreAJourCommande(dto);
        return "redirect:/commandes";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable("id") Long id, Model model) {
        Commandes commande = commandesService.findById(id);
        
        CommandeCreateRequestDto dto = new CommandeCreateRequestDto();
        dto.setId(commande.getId());
        dto.setIdClient(commande.getClient() != null ? commande.getClient().getId() : null);
        dto.setIdZoneLivraison(commande.getZoneLivraison() != null ? commande.getZoneLivraison().getId() : null);
        dto.setDateCommande(commande.getDateCommande());

        List<CommandeLigneRequestDto> lignesDto = commandesService.findDetailsByCommandeId(id).stream()
                .map(d -> {
                    CommandeLigneRequestDto l = new CommandeLigneRequestDto();
                    l.setIdPlat(d.getPlat().getId());
                    l.setQuantite(d.getQuantite());
                    return l;
                })
                .collect(Collectors.toList());
        dto.setLignes(lignesDto);

        model.addAttribute("commandeDto", dto);
        model.addAttribute("clients", clientsRepository.findAll());
        model.addAttribute("zones", zonesLivraisonRepository.findAll());
        model.addAttribute("plats", platsRepository.findAll());
        return "commandes/form";
    }

     @GetMapping({"/{id}/detail", "/detail/{id}"})
    public String showDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("commande", commandesService.findById(id));
        model.addAttribute("details", commandesService.findDetailsByCommandeId(id));
        return "commandes/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        commandesService.deleteById(id);
        return "redirect:/commandes";
    }
}