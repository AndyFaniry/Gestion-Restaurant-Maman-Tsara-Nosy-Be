package com.gestion.restaurant.repository.caisse;

import com.gestion.restaurant.entity.caisse.MouvementCaisse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MouvementCaisseRepository extends JpaRepository<MouvementCaisse, Long> {
}
