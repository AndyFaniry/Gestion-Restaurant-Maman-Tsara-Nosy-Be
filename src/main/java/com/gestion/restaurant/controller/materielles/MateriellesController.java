package com.gestion.restaurant.controller.materielles;

import com.gestion.restaurant.dto.materielles.*;
import com.gestion.restaurant.service.materielles.MateriellesService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/materielles")
public class MateriellesController {

    private final MateriellesService materiellesService;

    public MateriellesController(MateriellesService materiellesService) {
        this.materiellesService = materiellesService;
    }

    @GetMapping
    public String listMaterielles(@ModelAttribute("criteria") MaterielSearchCriteria criteria, Model model) {
        model.addAttribute("materiellesList", materiellesService.search(criteria));
        model.addAttribute("categories", materiellesService.findAllCategories());
        model.addAttribute("statuts", materiellesService.findAllStatuts());
        return "materielles/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("materiel", new MaterielRequestDto());
        model.addAttribute("categories", materiellesService.findAllCategories());
        model.addAttribute("statuts", materiellesService.findAllStatuts());
        return "materielles/form";
    }

    @PostMapping("/save")
    public String saveMateriel(@ModelAttribute("materiel") MaterielRequestDto dto) {
        MaterielResponseDto enregistre = materiellesService.saveFromDto(dto);
        return "redirect:/materielles/" + enregistre.getId() + "/detail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("materiel", materiellesService.findDtoById(id));
        model.addAttribute("categories", materiellesService.findAllCategories());
        model.addAttribute("statuts", materiellesService.findAllStatuts());
        return "materielles/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteMateriel(@PathVariable("id") Long id) {
        materiellesService.deleteById(id);
        return "redirect:/materielles";
    }

    @GetMapping("/{id}/detail")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("materiel", materiellesService.findById(id));
        model.addAttribute("historiqueList", materiellesService.findHistorique(id));
        model.addAttribute("maintenancesList", materiellesService.findMaintenances(id));
        model.addAttribute("inventaireList", materiellesService.findInventaire(id));
        model.addAttribute("stockActuel", materiellesService.getStockActuel(id));
        model.addAttribute("fournisseurs", materiellesService.findAllFournisseurs());
        return "materielles/detail";
    }

    @PostMapping("/{id}/historique/save")
    public String saveAchat(@PathVariable("id") Long id,
            @RequestParam("quantite") BigDecimal quantite,
            @RequestParam("prixAchat") BigDecimal prixAchat,
            @RequestParam(value = "idFournisseur", required = false) Long idFournisseur,
            @RequestParam("dateEntree") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEntree) {
        materiellesService.enregistrerAchat(id, dateEntree, quantite, prixAchat, idFournisseur);
        return "redirect:/materielles/" + id + "/detail";
    }

    @PostMapping("/{id}/maintenance/save")
    public String saveMaintenance(@PathVariable("id") Long id,
            @RequestParam("description") String description,
            @RequestParam("cout") BigDecimal cout,
            @RequestParam(value = "technicien", required = false) String technicien,
            @RequestParam("dateMaintenance") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateMaintenance) {
        materiellesService.enregistrerMaintenance(id, dateMaintenance, description, cout, technicien);
        return "redirect:/materielles/" + id + "/detail";
    }

    @GetMapping("/{id}/hors-service")
    public String horsService(@PathVariable("id") Long id) {
        materiellesService.mettreHorsService(id);
        return "redirect:/materielles/" + id + "/detail";
    }
}