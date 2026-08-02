package com.gestion.restaurant.dto.livraisons;


import java.math.BigDecimal;

public class ZoneLivraisonFilterDto {

    private String search;
    private BigDecimal maxPrix;

    public String getSearch() { return search; }
    public void setSearch(String search) { this.search = search; }

    public BigDecimal getMaxPrix() { return maxPrix; }
    public void setMaxPrix(BigDecimal maxPrix) { this.maxPrix = maxPrix; }
}