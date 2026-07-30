package com.gestion.restaurant.repository.ingredients;

import com.gestion.restaurant.entity.ingredients.HistoriqueIngredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriqueIngredientsRepository extends JpaRepository<HistoriqueIngredients, Long> {
}
