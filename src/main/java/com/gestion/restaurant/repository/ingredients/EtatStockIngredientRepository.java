package com.gestion.restaurant.repository.ingredients;

import com.gestion.restaurant.entity.ingredients.EtatStockIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtatStockIngredientRepository extends JpaRepository<EtatStockIngredient, Long> {
    Optional<EtatStockIngredient> findTopByIngredient_IdOrderByDateEtatStockDescIdDesc(Long idIngredient);
}