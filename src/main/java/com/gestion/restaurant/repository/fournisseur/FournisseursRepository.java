package com.gestion.restaurant.repository.fournisseur;

import com.gestion.restaurant.entity.fournisseurs.Fournisseurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FournisseursRepository extends JpaRepository<Fournisseurs, Long> {

    @Query("SELECT f FROM Fournisseurs f LEFT JOIN FETCH f.typeFournisseurs ORDER BY f.nom")
    List<Fournisseurs> findAllWithType();

    @Query("SELECT f FROM Fournisseurs f LEFT JOIN FETCH f.typeFournisseurs WHERE f.id = :id")
    Optional<Fournisseurs> findByIdWithType(@Param("id") Long id);
}
