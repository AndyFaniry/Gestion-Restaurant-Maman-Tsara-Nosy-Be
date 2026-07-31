package com.gestion.restaurant.repository.materielles;

import com.gestion.restaurant.entity.materielles.HistoriqueMaterielles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriqueMateriellesRepository extends JpaRepository<HistoriqueMaterielles, Long> {
    List<HistoriqueMaterielles> findByMateriel_IdOrderByDateAchatDesc(Long idMateriel);
}