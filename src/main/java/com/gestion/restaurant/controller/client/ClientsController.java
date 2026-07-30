package com.gestion.restaurant.controller.client;

import com.gestion.restaurant.entity.clients.Clients;
import com.gestion.restaurant.repository.clients.ClientsRepository;
import com.gestion.restaurant.repository.clients.TypeClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientsController {

    private final ClientsRepository clientsRepository;
    private final TypeClientRepository typeClientRepository;

    public ClientsController(ClientsRepository clientsRepository, TypeClientRepository typeClientRepository) {
        this.clientsRepository = clientsRepository;
        this.typeClientRepository = typeClientRepository;
    }

    @GetMapping
    public String listClients(Model model) {
        model.addAttribute("clientsList", clientsRepository.findAll());
        return "clients/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new Clients());
        model.addAttribute("typesClient", typeClientRepository.findAll());
        return "clients/form";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute("client") Clients client) {
        clientsRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Clients client = clientsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Client invalide:" + id));
        model.addAttribute("client", client);
        model.addAttribute("typesClient", typeClientRepository.findAll());
        return "clients/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id) {
        clientsRepository.deleteById(id);
        return "redirect:/clients";
    }
}