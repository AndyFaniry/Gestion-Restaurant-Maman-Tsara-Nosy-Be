package com.gestion.restaurant.repository.livraisons;

import com.gestion.restaurant.entity.livraisons.ZonesLivraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonesLivraisonRepository extends JpaRepository<ZonesLivraison, Long> {
}
