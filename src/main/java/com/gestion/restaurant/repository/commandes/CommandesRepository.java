package com.gestion.restaurant.repository.commandes;

import com.gestion.restaurant.entity.commandes.Commandes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandesRepository extends JpaRepository<Commandes, Long> {
}
