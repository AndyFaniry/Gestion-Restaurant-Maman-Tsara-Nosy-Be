package com.gestion.restaurant.repository.plats;

import com.gestion.restaurant.entity.plats.RecettePlats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecettePlatsRepository extends JpaRepository<RecettePlats, Long> {
}
