package com.gestion.restaurant.repository.recettes;



import com.gestion.restaurant.entity.plats.RecettePlats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecettePlatsRepository extends JpaRepository<RecettePlats, Long> {

    // Récupérer tous les ingrédients composants la recette d'un plat spécifique
    List<RecettePlats> findByPlatId(Long idPlat);

    // Récupérer les recettes qui utilisent un ingrédient donné
    List<RecettePlats> findByIngredientId(Long idIngredient);

    // Supprimer tous les ingrédients associés à un plat
    void deleteByPlatId(Long idPlat);

    // Supprimer une entrée spécifique de recette par ingrédient
    void deleteByIngredientId(Long idIngredient);
}