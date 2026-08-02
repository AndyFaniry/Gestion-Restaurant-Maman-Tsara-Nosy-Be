package com.gestion.restaurant.repository.personnels;

import com.gestion.restaurant.entity.personnels.RaisonAbsence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaisonAbsenceRepository extends JpaRepository<RaisonAbsence, Long> {
}