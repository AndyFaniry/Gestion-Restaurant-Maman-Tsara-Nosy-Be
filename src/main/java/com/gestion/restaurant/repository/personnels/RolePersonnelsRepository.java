package com.gestion.restaurant.repository.personnels;

import com.gestion.restaurant.entity.personnels.RolePersonnels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePersonnelsRepository extends JpaRepository<RolePersonnels, Long> {
}
