package com.gestion.restaurant.controller.ingredients;

import com.gestion.restaurant.service.ingredients.IngredientsService;
import com.gestion.restaurant.dto.ingredients.IngredientRequestDto;
import com.gestion.restaurant.dto.ingredients.IngredientResponseDto;
import com.gestion.restaurant.dto.ingredients.IngredientSearchCriteria;
import com.gestion.restaurant.dto.ingredients.IngredientStockDTO;
import com.gestion.restaurant.entity.ingredients.Ingredients;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ingredients")
public class IngredientsController {

    private final IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @GetMapping
    public String listIngredients(@ModelAttribute("criteria") IngredientSearchCriteria criteria, Model model) {
        model.addAttribute("ingredientsList", ingredientsService.search(criteria));
        model.addAttribute("categories", ingredientsService.findAllCategories());
        model.addAttribute("statuts", ingredientsService.findAllStatuts());
        return "ingredients/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ingredient", new IngredientRequestDto());
        model.addAttribute("categories", ingredientsService.findAllCategories());
        model.addAttribute("statuts", ingredientsService.findAllStatuts());
        model.addAttribute("fournisseurs", ingredientsService.findAllFournisseurs());
        model.addAttribute("unites", ingredientsService.findAllUnites());
        return "ingredients/form";
    }

    @PostMapping("/save")
    public String saveIngredient(@ModelAttribute("ingredient") IngredientRequestDto dto) {
        IngredientResponseDto enregistre = ingredientsService.saveFromDto(dto);
        return "redirect:/ingredients/" + enregistre.getId() + "/detail";
    }

@GetMapping("/edit/{id}")
public String showEditForm(@PathVariable("id") Long id, Model model) {
    Ingredients entity = ingredientsService.findById(id);
    
    IngredientRequestDto dto = new IngredientRequestDto();
    dto.setId(entity.getId());
    dto.setNom(entity.getNom());
    if (entity.getCategorieIngredients() != null) dto.setIdCategorie(entity.getCategorieIngredients().getId());
    if (entity.getStatutIngredient() != null) dto.setIdStatut(entity.getStatutIngredient().getId());
    if (entity.getFournisseur() != null) dto.setIdFournisseur(entity.getFournisseur().getId());
    if (entity.getUnite() != null) dto.setIdUnite(entity.getUnite().getId());

    model.addAttribute("ingredient", dto);
    model.addAttribute("categories", ingredientsService.findAllCategories());
    model.addAttribute("statuts", ingredientsService.findAllStatuts());
    model.addAttribute("fournisseurs", ingredientsService.findAllFournisseurs());
    model.addAttribute("unites", ingredientsService.findAllUnites());
    return "ingredients/form";
}

    @GetMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable("id") Long id) {
        ingredientsService.deleteById(id);
        return "redirect:/ingredients";
    }

    @GetMapping("/{id}/detail")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ingredient", ingredientsService.findById(id));
        model.addAttribute("historiqueList", ingredientsService.findHistorique(id));
        model.addAttribute("inventaireList", ingredientsService.findInventaire(id));
        model.addAttribute("stockActuel", ingredientsService.getStockActuel(id));
        return "ingredients/detail";
    }

    @PostMapping("/{id}/achat/save")
    public String saveAchat(@PathVariable("id") Long id,
            @RequestParam("quantite") BigDecimal quantite,
            @RequestParam("prixAchat") BigDecimal prixAchat,
            @RequestParam(value = "dateEntree", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEntree,
            @RequestParam(value = "datePeremption", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePeremption) {
        ingredientsService.enregistrerAchatEntree(id, dateEntree, datePeremption, quantite, prixAchat);
        return "redirect:/ingredients/" + id + "/detail";
    }

    @PostMapping("/{id}/sortie/save")
    public String saveSortie(@PathVariable("id") Long id,
            @RequestParam("quantite") BigDecimal quantite,
            @RequestParam("typeMouvement") String typeMouvement,
            @RequestParam(value = "dateMvt", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMvt) {
        ingredientsService.enregistrerSortieOuPerte(id, quantite, typeMouvement, dateMvt);
        return "redirect:/ingredients/" + id + "/detail";
    }

    @GetMapping("/stock")
    public String viewStockGlobal(Model model) {
        List<IngredientStockDTO> stockItems = ingredientsService.getGlobalStockState();

        long nbRuptureOuFaible = stockItems.stream().filter(i -> i.getQuantiteActuelle() < 5).count();
        long nbStockOk = stockItems.stream().filter(i -> i.getQuantiteActuelle() >= 5).count();

        model.addAttribute("stockItems", stockItems);
        model.addAttribute("nombreAlerteStock", nbRuptureOuFaible);
        model.addAttribute("nombreStockOk", nbStockOk);

        return "ingredients/stock";
    }
}