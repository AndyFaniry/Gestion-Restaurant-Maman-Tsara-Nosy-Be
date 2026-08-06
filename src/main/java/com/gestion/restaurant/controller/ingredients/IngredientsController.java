package com.gestion.restaurant.controller.ingredients;

import com.gestion.restaurant.dto.ingredients.IngredientRequestDto;
import com.gestion.restaurant.dto.ingredients.IngredientSearchCriteria;
import com.gestion.restaurant.dto.ingredients.IngredientStockDTO;
import com.gestion.restaurant.service.ingredients.IngredientsService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
        populateFormLookups(model);
        model.addAttribute("ingredient", new IngredientRequestDto());
        return "ingredients/form";
    }

    @PostMapping("/save")
    public String saveIngredient(@Valid @ModelAttribute("ingredient") IngredientRequestDto dto,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateFormLookups(model);
            return "ingredients/form";
        }
        var enregistre = ingredientsService.saveFromDto(dto);
        redirectAttributes.addFlashAttribute("successMessage", "Ingrédient enregistré.");
        return "redirect:/ingredients/" + enregistre.getId() + "/detail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        populateFormLookups(model);
        model.addAttribute("ingredient", ingredientsService.toRequestDto(id));
        return "ingredients/form";
    }

    @PostMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        ingredientsService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Ingrédient supprimé.");
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
                            @RequestParam(value = "dateEntree", required = false)
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEntree,
                            @RequestParam(value = "datePeremption", required = false)
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePeremption,
                            RedirectAttributes redirectAttributes) {
        ingredientsService.enregistrerAchatEntree(id, dateEntree, datePeremption, quantite, prixAchat);
        redirectAttributes.addFlashAttribute("successMessage", "Achat enregistré (stock + caisse).");
        return "redirect:/ingredients/" + id + "/detail";
    }

    @PostMapping("/{id}/sortie/save")
    public String saveSortie(@PathVariable("id") Long id,
                             @RequestParam("quantite") BigDecimal quantite,
                             @RequestParam("typeMouvement") String typeMouvement,
                             @RequestParam(value = "dateMvt", required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMvt,
                             RedirectAttributes redirectAttributes) {
        ingredientsService.enregistrerSortieOuPerte(id, quantite, typeMouvement, dateMvt);
        redirectAttributes.addFlashAttribute("successMessage", "Sortie / perte enregistrée.");
        return "redirect:/ingredients/" + id + "/detail";
    }

    @GetMapping("/stock")
    public String viewStockGlobal(Model model) {
        List<IngredientStockDTO> stockItems = ingredientsService.getGlobalStockState();
        long nbRuptureOuFaible = stockItems.stream()
                .filter(i -> i.getQuantiteActuelle() < IngredientsService.SEUIL_STOCK_FAIBLE)
                .count();
        long nbStockOk = stockItems.size() - nbRuptureOuFaible;

        model.addAttribute("stockItems", stockItems);
        model.addAttribute("nombreAlerteStock", nbRuptureOuFaible);
        model.addAttribute("nombreStockOk", nbStockOk);
        model.addAttribute("seuilStockFaible", IngredientsService.SEUIL_STOCK_FAIBLE);
        return "ingredients/stock";
    }

    private void populateFormLookups(Model model) {
        model.addAttribute("categories", ingredientsService.findAllCategories());
        model.addAttribute("statuts", ingredientsService.findAllStatuts());
        model.addAttribute("fournisseurs", ingredientsService.findAllFournisseurs());
        model.addAttribute("unites", ingredientsService.findAllUnites());
    }
}
