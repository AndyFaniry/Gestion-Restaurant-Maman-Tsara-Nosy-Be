package com.gestion.restaurant.controller.personnels;

import com.gestion.restaurant.dto.personnels.*;
import com.gestion.restaurant.service.personnels.PersonnelsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/personnels")
public class PersonnelsController {

    private final PersonnelsService personnelsService;

    public PersonnelsController(PersonnelsService personnelsService) {
        this.personnelsService = personnelsService;
    }

    @GetMapping
    public String list(@ModelAttribute("criteria") PersonnelSearchCriteria criteria, Model model) {
        model.addAttribute("personnelsList", personnelsService.search(criteria));
        model.addAttribute("roles", personnelsService.findAllRoles());
        return "personnels/list";
    }

    @GetMapping("/new")
    public String showCreate(Model model) {
        model.addAttribute("personnel", new PersonnelRequestDto());
        model.addAttribute("roles", personnelsService.findAllRoles());
        return "personnels/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("personnel") PersonnelRequestDto dto) {
        PersonnelResponseDto saved = personnelsService.saveFromDto(dto);
        return "redirect:/personnels/" + saved.getId() + "/detail";
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("personnel", personnelsService.findDtoById(id));
        model.addAttribute("roles", personnelsService.findAllRoles());
        return "personnels/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        personnelsService.deleteById(id);
        return "redirect:/personnels";
    }

    @GetMapping("/{id}/detail")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("personnel", personnelsService.findById(id));
        model.addAttribute("historiquePaie", personnelsService.findHistoriquePaie(id));
        model.addAttribute("absencesList", personnelsService.findAbsences(id));
        model.addAttribute("raisonsAbsence", personnelsService.findAllRaisonsAbsence());
        return "personnels/detail";
    }

    @PostMapping("/{id}/paie/save")
    public String genererPaie(@PathVariable("id") Long id,
                              @RequestParam("salaire") BigDecimal salaire,
                              @RequestParam(value = "prime", required = false) BigDecimal prime,
                              @RequestParam(value = "datePaie", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datePaie) {
        personnelsService.genererFichePaie(id, salaire, prime, datePaie);
        return "redirect:/personnels/" + id + "/detail";
    }

    @PostMapping("/{id}/absence/save")
    public String enregistrerAbsence(@PathVariable("id") Long id,
                                     @RequestParam("dateDebut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                                     @RequestParam("dateFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
                                     @RequestParam("idRaison") Long idRaison,
                                     @RequestParam(value = "commentaire", required = false) String commentaire) {
        personnelsService.enregistrerAbsence(id, dateDebut, dateFin, idRaison, commentaire);
        return "redirect:/personnels/" + id + "/detail";
    }
}