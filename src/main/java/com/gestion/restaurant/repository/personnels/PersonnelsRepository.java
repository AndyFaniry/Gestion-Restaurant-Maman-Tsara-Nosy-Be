package com.gestion.restaurant.repository.personnels;

import com.gestion.restaurant.entity.personnels.Personnels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonnelsRepository extends JpaRepository<Personnels, Long> {
}
