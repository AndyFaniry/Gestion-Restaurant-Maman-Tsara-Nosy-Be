package com.gestion.restaurant.service.ingredients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.restaurant.repository.ingredients.UniteRepository;
import com.gestion.restaurant.entity.ingredients.Unite;;

@Service
public class UniteService {
    @Autowired
    private UniteRepository uniteRepository;


    @Transactional(readOnly = true)
    public List<Unite> findAllSourceApprovisionnement() {
        return uniteRepository.findAll();
    }

    @Transactional
    private void SaveUnite(Unite unite) throws Exception {
        if (unite == null) {
            throw new Exception("unite doit etre obligatoire");
        }
        uniteRepository.save(unite);
    }

    public static Double KgtoG(double kg) {
        return kg * 1000;
    }

    public static Double GtoKg(double g) {
        return g / 1000;
    }

    public static Double LtoMl(double l) {
        return l * 1000;
    }

    public static Double MltoL(double ml) {
        return ml / 1000;
    }
}
