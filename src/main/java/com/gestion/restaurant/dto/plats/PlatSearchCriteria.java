package com.gestion.restaurant.dto.plats;

import lombok.Data;

@Data
public class PlatSearchCriteria {
    private String nom;
    private Long idCategorie;
}