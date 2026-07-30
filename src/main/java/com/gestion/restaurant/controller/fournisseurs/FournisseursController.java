package com.gestion.restaurant.controller.fournisseurs;

import com.gestion.restaurant.entity.fournisseurs.Fournisseurs;
import com.gestion.restaurant.repository.fournisseur.FournisseursRepository;
import com.gestion.restaurant.repository.fournisseur.TypeFournisseursRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fournisseurs")
public class FournisseursController {

    private final FournisseursRepository fournisseursRepository;
    private final TypeFournisseursRepository typeFournisseursRepository;

    public FournisseursController(FournisseursRepository fournisseursRepository, TypeFournisseursRepository typeFournisseursRepository) {
        this.fournisseursRepository = fournisseursRepository;
        this.typeFournisseursRepository = typeFournisseursRepository;
    }

    @GetMapping
    public String listFournisseurs(Model model) {
        model.addAttribute("fournisseursList", fournisseursRepository.findAll());
        return "fournisseurs/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("fournisseur", new Fournisseurs());
        model.addAttribute("typesFournisseur", typeFournisseursRepository.findAll());
        return "fournisseurs/form";
    }

    @PostMapping("/save")
    public String saveFournisseur(@ModelAttribute("fournisseur") Fournisseurs fournisseur) {
        fournisseursRepository.save(fournisseur);
        return "redirect:/fournisseurs";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Fournisseurs fournisseur = fournisseursRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Fournisseur invalide : " + id));
        model.addAttribute("fournisseur", fournisseur);
        model.addAttribute("typesFournisseur", typeFournisseursRepository.findAll());
        return "fournisseurs/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteFournisseur(@PathVariable("id") Long id) {
        fournisseursRepository.deleteById(id);
        return "redirect:/fournisseurs";
    }
}