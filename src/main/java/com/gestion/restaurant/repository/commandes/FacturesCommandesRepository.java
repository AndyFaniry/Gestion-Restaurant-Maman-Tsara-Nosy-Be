package com.gestion.restaurant.repository.commandes;

import com.gestion.restaurant.entity.commandes.FacturesCommandes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturesCommandesRepository extends JpaRepository<FacturesCommandes, Long> {

    void deleteByCommande_Id(Long commandeId);
}
