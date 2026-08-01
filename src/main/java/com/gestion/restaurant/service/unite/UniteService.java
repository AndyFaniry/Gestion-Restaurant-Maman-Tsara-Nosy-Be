package com.gestion.restaurant.service.unite;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class UniteService {

    // Facteurs de conversion vers une unité de référence (ex: gramme pour la masse, ml pour le volume)
    private static final Map<String, Double> FACTEURS_CONVERSION = new HashMap<>();

    static {
        // Masse (unité de base : Gramme)
        FACTEURS_CONVERSION.put("g", 1.0);
        FACTEURS_CONVERSION.put("kg", 1000.0);
        FACTEURS_CONVERSION.put("mg", 0.001);

        // Volume (unité de base : Millilitre)
        FACTEURS_CONVERSION.put("ml", 1.0);
        FACTEURS_CONVERSION.put("l", 1000.0);
        FACTEURS_CONVERSION.put("cl", 10.0);
        FACTEURS_CONVERSION.put("dl", 100.0);

        // Unités simples (Unité, Pièce, Portion)
        FACTEURS_CONVERSION.put("u", 1.0);
        FACTEURS_CONVERSION.put("pcs", 1.0);
    }

    /**
     * Convertit une quantité d'une unité source vers une unité cible.
     */
    public double convertir(String symboleSource, String symboleCible, double quantite) {
        if (symboleSource == null || symboleCible == null) {
            throw new IllegalArgumentException("Les symboles d'unités ne peuvent pas être nuls.");
        }

        String source = symboleSource.toLowerCase().trim();
        String cible = symboleCible.toLowerCase().trim();

        if (source.equals(cible)) {
            return quantite;
        }

        if (!FACTEURS_CONVERSION.containsKey(source) || !FACTEURS_CONVERSION.containsKey(cible)) {
            throw new IllegalArgumentException("Conversion non supportée entre " + symboleSource + " et " + symboleCible);
        }

        // Conversion vers l'unité de référence, puis vers l'unité cible
        double quantiteEnBase = quantite * FACTEURS_CONVERSION.get(source);
        return quantiteEnBase / FACTEURS_CONVERSION.get(cible);
    }
}