package com.gestion.restaurant.controller.fournisseurs;

import com.gestion.restaurant.entity.fournisseurs.Fournisseurs;
import com.gestion.restaurant.service.fournisseurs.FournisseursService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fournisseurs")
public class FournisseursController {

    private final FournisseursService fournisseursService;

    public FournisseursController(FournisseursService fournisseursService) {
        this.fournisseursService = fournisseursService;
    }

    @GetMapping
    public String listFournisseurs(Model model) {
        model.addAttribute("fournisseursList", fournisseursService.findAll());
        return "fournisseurs/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("fournisseur", new Fournisseurs());
        model.addAttribute("typesFournisseur", fournisseursService.findAllTypes());
        return "fournisseurs/form";
    }

    @PostMapping("/save")
    public String saveFournisseur(@ModelAttribute("fournisseur") Fournisseurs fournisseur) {
        fournisseursService.save(fournisseur);
        return "redirect:/fournisseurs";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("fournisseur", fournisseursService.findById(id));
        model.addAttribute("typesFournisseur", fournisseursService.findAllTypes());
        return "fournisseurs/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteFournisseur(@PathVariable("id") Long id) {
        fournisseursService.deleteById(id);
        return "redirect:/fournisseurs";
    }
}