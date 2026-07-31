package com.gestion.restaurant.repository.caisse;

import com.gestion.restaurant.entity.caisse.TypeMouvementCaisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeMouvementCaisseRepository extends JpaRepository<TypeMouvementCaisse, Long> {
    Optional<TypeMouvementCaisse> findByLibelle(String libelle);
}