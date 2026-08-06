package com.gestion.restaurant.controller.recette;

import com.gestion.restaurant.dto.recette.RecetteRequestDto;
import com.gestion.restaurant.service.ingredients.IngredientsService;
import com.gestion.restaurant.service.plats.PlatsService;
import com.gestion.restaurant.service.recette.RecetteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recettes")
public class RecetteController {

    private final RecetteService recetteService;
    private final PlatsService platsService;
    private final IngredientsService ingredientsService;

    public RecetteController(RecetteService recetteService,
                             PlatsService platsService,
                             IngredientsService ingredientsService) {
        this.recetteService = recetteService;
        this.platsService = platsService;
        this.ingredientsService = ingredientsService;
    }

    @GetMapping("/plat/{idPlat}")
    public String afficherRecettePlat(@PathVariable("idPlat") Long idPlat, Model model) {
        model.addAttribute("plat", platsService.findById(idPlat));
        model.addAttribute("recettes", recetteService.getIngredientsParPlat(idPlat));
        model.addAttribute("ingredients", ingredientsService.findAll());
        
        RecetteRequestDto requestDto = new RecetteRequestDto();
        requestDto.setIdPlat(idPlat);
        model.addAttribute("nouvelIngredient", requestDto);

        return "recettes/details";
    }

    @PostMapping("/ajouter")
    public String ajouterIngredient(@ModelAttribute("nouvelIngredient") RecetteRequestDto dto,
                                    RedirectAttributes redirectAttributes) {
        recetteService.ajouterIngredientARecette(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Ingrédient ajouté à la recette avec succès.");
        return "redirect:/recettes/plat/" + dto.getIdPlat();
    }

    @PostMapping("/supprimer/{idRecette}")
    public String supprimerIngredient(@PathVariable("idRecette") Long idRecette,
                                      @RequestParam("idPlat") Long idPlat,
                                      RedirectAttributes redirectAttributes) {
        recetteService.supprimerIngredientDeRecette(idRecette);
        redirectAttributes.addFlashAttribute("successMessage", "Ingrédient retiré de la recette.");
        return "redirect:/recettes/plat/" + idPlat;
    }
}