package com.gestion.restaurant.dto.personnels;

import lombok.Data;

@Data
public class PersonnelSearchCriteria {
    private String nom;
    private String prenom;
    private Long idRole;
}