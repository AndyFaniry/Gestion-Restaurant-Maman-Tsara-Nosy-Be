package com.gestion.restaurant.repository.plats;

import com.gestion.restaurant.entity.plats.CategoriePlats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriePlatsRepository extends JpaRepository<CategoriePlats, Long> {
}
