package com.gestion.restaurant.repository.personnels;

import com.gestion.restaurant.entity.personnels.FichePaie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichePaieRepository extends JpaRepository<FichePaie, Long> {
}
