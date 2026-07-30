package com.gestion.restaurant.repository.ingredients;

import com.gestion.restaurant.entity.ingredients.TypeMvtIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeMvtIngredientRepository extends JpaRepository<TypeMvtIngredient, Long> {
}
