package com.gestion.restaurant.repository.fournisseur;

import com.gestion.restaurant.entity.fournisseur.TypeFournisseurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeFournisseursRepository extends JpaRepository<TypeFournisseurs, Long> {
}
