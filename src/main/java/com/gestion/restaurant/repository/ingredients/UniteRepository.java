package com.gestion.restaurant.repository.ingredients;

import com.gestion.restaurant.entity.ingredients.Unite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniteRepository extends JpaRepository<Unite, Long> {
}