package com.gestion.restaurant.service.ingredients;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.restaurant.repository.ingredients.UniteRepository;
import com.gestion.restaurant.entity.ingredients.Unite;

@Service
public class UniteService {

    private final UniteRepository uniteRepository;

    public UniteService(UniteRepository uniteRepository) {
        this.uniteRepository = uniteRepository;
    }

    @Transactional(readOnly = true)
    public List<Unite> findAllSourceApprovisionnement() {
        return uniteRepository.findAll();
    }

    @Transactional
    public Unite saveUnite(Unite unite) {
        if (unite == null) {
            throw new IllegalArgumentException("L'unité ne peut pas être nulle");
        }
        return uniteRepository.save(unite);
    }

    public static Double kgToG(double kg) {
        return kg * 1000;
    }

    public static Double gToKg(double g) {
        return g / 1000;
    }

    public static Double lToMl(double l) {
        return l * 1000;
    }

    public static Double mlToL(double ml) {
        return ml / 1000;
    }
}