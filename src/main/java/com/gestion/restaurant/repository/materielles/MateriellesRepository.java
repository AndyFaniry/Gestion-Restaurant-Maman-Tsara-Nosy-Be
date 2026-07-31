package com.gestion.restaurant.repository.materielles;

import com.gestion.restaurant.entity.materielles.Materielles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MateriellesRepository extends JpaRepository<Materielles, Long> {
    List<Materielles> findByCategorieMaterielles_Id(Long idCategorie);
    List<Materielles> findByStatutMaterielles_Id(Long idStatut);
    List<Materielles> findByCategorieMaterielles_IdAndStatutMaterielles_Id(Long idCategorie, Long idStatut);
}
