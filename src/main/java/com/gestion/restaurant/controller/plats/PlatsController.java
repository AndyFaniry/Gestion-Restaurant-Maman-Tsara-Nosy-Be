package com.gestion.restaurant.controller.plats;

import com.gestion.restaurant.entity.plats.Plats;
import com.gestion.restaurant.repository.plats.CategoriePlatsRepository;
import com.gestion.restaurant.repository.plats.PlatsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/plats")
public class PlatsController {

    private final PlatsRepository platsRepository;
    private final CategoriePlatsRepository categoriePlatsRepository;

    public PlatsController(PlatsRepository platsRepository, CategoriePlatsRepository categoriePlatsRepository) {
        this.platsRepository = platsRepository;
        this.categoriePlatsRepository = categoriePlatsRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("platsList", platsRepository.findAll());
        return "plats/list";
    }

    @GetMapping("/new")
    public String showCreate(Model model) {
        model.addAttribute("plat", new Plats());
        model.addAttribute("categories", categoriePlatsRepository.findAll());
        return "plats/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("plat") Plats plat) {
        platsRepository.save(plat);
        return "redirect:/plats";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable("id") Long id, Model model) {
        Plats plat = platsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plat introuvable : " + id));
        model.addAttribute("plat", plat);
        model.addAttribute("categories", categoriePlatsRepository.findAll());
        return "plats/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        platsRepository.deleteById(id);
        return "redirect:/plats";
    }
}