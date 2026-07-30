package com.gestion.restaurant.controller.caisse;

import com.gestion.restaurant.entity.caisse.MouvementCaisse;
import com.gestion.restaurant.repository.caisse.MouvementCaisseRepository;
import com.gestion.restaurant.repository.caisse.TypeMouvementCaisseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/caisse")
public class CaisseController {

    private final MouvementCaisseRepository mouvementCaisseRepository;
    private final TypeMouvementCaisseRepository typeMouvementRepo;

    public CaisseController(MouvementCaisseRepository mouvementCaisseRepository, 
                            TypeMouvementCaisseRepository typeMouvementRepo) {
        this.mouvementCaisseRepository = mouvementCaisseRepository;
        this.typeMouvementRepo = typeMouvementRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("mouvementsList", mouvementCaisseRepository.findAll());
        return "caisse/list";
    }

    @GetMapping("/new")
    public String showCreate(Model model) {
        model.addAttribute("mouvement", new MouvementCaisse());
        model.addAttribute("typesMouvement", typeMouvementRepo.findAll());
        return "caisse/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("mouvement") MouvementCaisse mouvement) {
        mouvementCaisseRepository.save(mouvement);
        return "redirect:/caisse";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable("id") Long id, Model model) {
        MouvementCaisse m = mouvementCaisseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mouvement introuvable : " + id));
        model.addAttribute("mouvement", m);
        model.addAttribute("typesMouvement", typeMouvementRepo.findAll());
        return "caisse/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        mouvementCaisseRepository.deleteById(id);
        return "redirect:/caisse";
    }
}