package com.gestion.restaurant.controller.ingredients;

import com.gestion.restaurant.entity.ingredients.Ingredients;
import com.gestion.restaurant.repository.fournisseur.FournisseursRepository;
import com.gestion.restaurant.repository.ingredients.CategorieIngredientsRepository;
import com.gestion.restaurant.repository.ingredients.IngredientsRepository;
import com.gestion.restaurant.repository.ingredients.StatutIngredientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ingredients")
public class IngredientsController {

    private final IngredientsRepository ingredientsRepository;
    private final CategorieIngredientsRepository categorieRepo;
    private final StatutIngredientRepository statutRepo;
    private final FournisseursRepository fournisseurRepo;

    public IngredientsController(IngredientsRepository ingredientsRepository, 
                                 CategorieIngredientsRepository categorieRepo, 
                                 StatutIngredientRepository statutRepo, 
                                 FournisseursRepository fournisseurRepo) {
        this.ingredientsRepository = ingredientsRepository;
        this.categorieRepo = categorieRepo;
        this.statutRepo = statutRepo;
        this.fournisseurRepo = fournisseurRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("ingredientsList", ingredientsRepository.findAll());
        return "ingredients/list";
    }

    @GetMapping("/new")
    public String showCreate(Model model) {
        model.addAttribute("ingredient", new Ingredients());
        model.addAttribute("categories", categorieRepo.findAll());
        model.addAttribute("statuts", statutRepo.findAll());
        model.addAttribute("fournisseurs", fournisseurRepo.findAll());
        return "ingredients/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("ingredient") Ingredients ingredient) {
        ingredientsRepository.save(ingredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable("id") Long id, Model model) {
        Ingredients ing = ingredientsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingrédient introuvable : " + id));
        model.addAttribute("ingredient", ing);
        model.addAttribute("categories", categorieRepo.findAll());
        model.addAttribute("statuts", statutRepo.findAll());
        model.addAttribute("fournisseurs", fournisseurRepo.findAll());
        return "ingredients/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        ingredientsRepository.deleteById(id);
        return "redirect:/ingredients";
    }
}