package com.gestion.restaurant.entity.personnels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "raisonabsence")
@Data
@NoArgsConstructor
public class RaisonAbsence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String libelle;
}