package com.gestion.restaurant.repository.materielles;

import com.gestion.restaurant.entity.materielles.StatutMaterielles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatutMateriellesRepository extends JpaRepository<StatutMaterielles, Long> {
    Optional<StatutMaterielles> findByLibelle(String libelle);
}