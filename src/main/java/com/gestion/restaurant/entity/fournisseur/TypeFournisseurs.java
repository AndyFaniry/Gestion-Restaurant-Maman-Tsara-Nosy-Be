package com.gestion.restaurant.entity.fournisseur;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "typefournisseurs")
@Data
@NoArgsConstructor
public class TypeFournisseurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String libelle;
}