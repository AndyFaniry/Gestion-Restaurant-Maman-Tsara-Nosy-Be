package com.gestion.restaurant.entity.ingredients;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unite")
@Data
@NoArgsConstructor
public class Unite {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nom; // ex: "Gramme", "Litre", "Pièce"

    @Column(length = 10)
    private String symbole; // ex: "g", "L", "pcs"
}
