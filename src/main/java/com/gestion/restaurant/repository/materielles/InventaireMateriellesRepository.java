package com.gestion.restaurant.repository.materielles;

import com.gestion.restaurant.entity.materielles.InventaireMaterielles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventaireMateriellesRepository extends JpaRepository<InventaireMaterielles, Long> {
    List<InventaireMaterielles> findByMateriel_IdOrderByDateMouvementDesc(Long idMateriel);
}