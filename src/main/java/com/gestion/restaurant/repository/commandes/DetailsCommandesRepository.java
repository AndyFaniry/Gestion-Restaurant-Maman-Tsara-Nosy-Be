package com.gestion.restaurant.repository.commandes;

import com.gestion.restaurant.entity.commandes.DetailsCommandes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetailsCommandesRepository extends JpaRepository<DetailsCommandes, Long> {
    List<DetailsCommandes> findByCommandeId(Long commandeId);
    void deleteByCommandeId(Long commandeId);
}