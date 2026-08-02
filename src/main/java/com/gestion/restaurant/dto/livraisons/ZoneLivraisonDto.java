package com.gestion.restaurant.dto.livraisons;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ZoneLivraisonDto {

    private Long id;

    @NotBlank(message = "Le libellé est obligatoire")
    @Size(max = 50, message = "Le libellé ne doit pas dépasser 50 caractères")
    private String libelle;

    @NotNull(message = "La distance minimale est obligatoire")
    @PositiveOrZero(message = "La distance minimale doit être supérieure ou égale à 0")
    private BigDecimal min;

    @NotNull(message = "La distance maximale est obligatoire")
    @PositiveOrZero(message = "La distance maximale doit être supérieure ou égale à 0")
    private BigDecimal max;

    @NotNull(message = "Le prix est obligatoire")
    @PositiveOrZero(message = "Le prix doit être supérieur ou égal à 0")
    private BigDecimal prix;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public BigDecimal getMin() { return min; }
    public void setMin(BigDecimal min) { this.min = min; }

    public BigDecimal getMax() { return max; }
    public void setMax(BigDecimal max) { this.max = max; }

    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
}