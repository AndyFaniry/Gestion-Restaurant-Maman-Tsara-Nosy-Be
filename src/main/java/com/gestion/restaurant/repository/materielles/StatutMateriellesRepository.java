package com.gestion.restaurant.repository.materielles;

import com.gestion.restaurant.entity.materielles.StatutMaterielles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatutMateriellesRepository extends JpaRepository<StatutMaterielles, Long> {
}
