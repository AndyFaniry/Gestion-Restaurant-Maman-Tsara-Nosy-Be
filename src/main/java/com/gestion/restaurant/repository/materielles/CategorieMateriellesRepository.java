package com.gestion.restaurant.repository.materielles;

import com.gestion.restaurant.entity.materielles.CategorieMaterielles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieMateriellesRepository extends JpaRepository<CategorieMaterielles, Long> {
}
