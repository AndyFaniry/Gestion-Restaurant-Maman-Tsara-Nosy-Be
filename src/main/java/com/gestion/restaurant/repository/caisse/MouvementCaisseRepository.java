package com.gestion.restaurant.repository.caisse;

import com.gestion.restaurant.entity.caisse.MouvementCaisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MouvementCaisseRepository extends JpaRepository<MouvementCaisse, Long> {

    @Query("SELECT m FROM MouvementCaisse m LEFT JOIN FETCH m.typeMouvement ORDER BY m.id DESC")
    List<MouvementCaisse> findAllWithType();

    @Query("SELECT m FROM MouvementCaisse m LEFT JOIN FETCH m.typeMouvement WHERE m.id = :id")
    Optional<MouvementCaisse> findByIdWithType(@Param("id") Long id);
}
