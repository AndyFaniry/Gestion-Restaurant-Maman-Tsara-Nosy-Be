package com.gestion.restaurant.controller.client;

import com.gestion.restaurant.dto.clients.*;
import com.gestion.restaurant.service.clients.ClientsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clients")
public class ClientsController {

    private final ClientsService clientsService;

    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping
    public String listClients(@ModelAttribute("criteria") ClientSearchCriteria criteria, Model model) {
        model.addAttribute("clientsList", clientsService.search(criteria));
        model.addAttribute("typesClient", clientsService.findAllTypes());
        return "clients/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("client", new ClientRequestDto());
        model.addAttribute("typesClient", clientsService.findAllTypes());
        return "clients/form";
    }

    @PostMapping("/save")
    public String saveClient(@ModelAttribute("client") ClientRequestDto dto) {
        clientsService.saveFromDto(dto);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("client", clientsService.findDtoById(id));
        model.addAttribute("typesClient", clientsService.findAllTypes());
        return "clients/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id) {
        clientsService.deleteById(id);
        return "redirect:/clients";
    }
}