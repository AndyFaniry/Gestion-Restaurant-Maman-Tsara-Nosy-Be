package com.gestion.restaurant.repository.materielles;

import com.gestion.restaurant.entity.materielles.TypeMvtMaterielles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeMvtMateriellesRepository extends JpaRepository<TypeMvtMaterielles, Long> {
    Optional<TypeMvtMaterielles> findByLibelle(String libelle);
}