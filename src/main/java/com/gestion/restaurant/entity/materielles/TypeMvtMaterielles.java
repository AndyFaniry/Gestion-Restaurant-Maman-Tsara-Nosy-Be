package com.gestion.restaurant.entity.materielles;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "typemvtmaterielles")
@Data
@NoArgsConstructor
public class TypeMvtMaterielles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String libelle; // Entree, Maintenance, HorsService
}