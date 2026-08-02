package com.gestion.restaurant.repository.personnels;

import com.gestion.restaurant.entity.personnels.FichePaie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FichePaieRepository extends JpaRepository<FichePaie, Long> {
    List<FichePaie> findByPersonnel_IdOrderByDatePaieDesc(Long idPersonnel);
}