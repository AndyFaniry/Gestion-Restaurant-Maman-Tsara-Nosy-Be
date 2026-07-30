package com.gestion.restaurant.controller.ingredients;

import com.gestion.restaurant.entity.ingredients.EtatStockIngredient;
import com.gestion.restaurant.repository.ingredients.EtatStockIngredientRepository;
import com.gestion.restaurant.repository.ingredients.IngredientsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stocks")
public class StockController {

    private final EtatStockIngredientRepository etatStockRepo;
    private final IngredientsRepository ingredientsRepo;

    public StockController(EtatStockIngredientRepository etatStockRepo, IngredientsRepository ingredientsRepo) {
        this.etatStockRepo = etatStockRepo;
        this.ingredientsRepo = ingredientsRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("stocksList", etatStockRepo.findAll());
        return "stocks/list";
    }

    @GetMapping("/new")
    public String showCreate(Model model) {
        model.addAttribute("stock", new EtatStockIngredient());
        model.addAttribute("ingredients", ingredientsRepo.findAll());
        return "stocks/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("stock") EtatStockIngredient stock) {
        etatStockRepo.save(stock);
        return "redirect:/stocks";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable("id") Long id, Model model) {
        EtatStockIngredient s = etatStockRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Saisie de stock introuvable : " + id));
        model.addAttribute("stock", s);
        model.addAttribute("ingredients", ingredientsRepo.findAll());
        return "stocks/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        etatStockRepo.deleteById(id);
        return "redirect:/stocks";
    }
}