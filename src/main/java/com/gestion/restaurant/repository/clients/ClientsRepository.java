package com.gestion.restaurant.repository.clients;

import com.gestion.restaurant.entity.clients.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long>, JpaSpecificationExecutor<Clients> {

    @Query("SELECT c FROM Clients c LEFT JOIN FETCH c.typeClient WHERE c.id = :id")
    Optional<Clients> findByIdWithType(@Param("id") Long id);
}
