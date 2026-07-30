package com.gestion.restaurant.repository.clients;

import com.gestion.restaurant.entity.clients.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {
}
