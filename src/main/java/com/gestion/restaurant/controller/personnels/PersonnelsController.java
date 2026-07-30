package com.gestion.restaurant.controller.personnels;

import com.gestion.restaurant.entity.personnels.Personnels;
import com.gestion.restaurant.repository.personnels.PersonnelsRepository;
import com.gestion.restaurant.repository.personnels.RolePersonnelsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/personnels")
public class PersonnelsController {

    private final PersonnelsRepository personnelsRepository;
    private final RolePersonnelsRepository roleRepo;

    public PersonnelsController(PersonnelsRepository personnelsRepository, RolePersonnelsRepository roleRepo) {
        this.personnelsRepository = personnelsRepository;
        this.roleRepo = roleRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("personnelsList", personnelsRepository.findAll());
        return "personnels/list";
    }

    @GetMapping("/new")
    public String showCreate(Model model) {
        model.addAttribute("personnel", new Personnels());
        model.addAttribute("roles", roleRepo.findAll());
        return "personnels/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("personnel") Personnels personnel) {
        personnelsRepository.save(personnel);
        return "redirect:/personnels";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable("id") Long id, Model model) {
        Personnels p = personnelsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employé introuvable : " + id));
        model.addAttribute("personnel", p);
        model.addAttribute("roles", roleRepo.findAll());
        return "personnels/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        personnelsRepository.deleteById(id);
        return "redirect:/personnels";
    }
}