package com.gestion.restaurant.repository.materielles;

import com.gestion.restaurant.entity.materielles.Materielles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MateriellesRepository extends JpaRepository<Materielles, Long>, JpaSpecificationExecutor<Materielles> {

    @Query("SELECT m FROM Materielles m "
            + "LEFT JOIN FETCH m.categorieMaterielles "
            + "LEFT JOIN FETCH m.statutMaterielles "
            + "WHERE m.id = :id")
    Optional<Materielles> findByIdWithRelations(@Param("id") Long id);
}
