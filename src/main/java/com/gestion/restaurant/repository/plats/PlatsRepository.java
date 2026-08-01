package com.gestion.restaurant.repository.plats;

import com.gestion.restaurant.entity.plats.Plats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // Import à ajouter
import org.springframework.stereotype.Repository;

@Repository
public interface PlatsRepository extends JpaRepository<Plats, Long>, JpaSpecificationExecutor<Plats> {
}