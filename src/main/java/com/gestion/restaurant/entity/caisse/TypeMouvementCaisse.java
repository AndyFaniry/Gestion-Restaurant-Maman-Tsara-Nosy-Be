package com.gestion.restaurant.entity.caisse;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "typemouvementcaisse")
@Data
@NoArgsConstructor
public class TypeMouvementCaisse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String libelle;
}
