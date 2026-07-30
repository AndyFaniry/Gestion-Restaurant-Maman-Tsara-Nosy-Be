package com.gestion.restaurant.repository.materielles;

import com.gestion.restaurant.entity.materielles.Materielles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriellesRepository extends JpaRepository<Materielles, Long> {
}
