package com.gestion.restaurant.entity.ingredients;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "typemvtingredient")
@Data
@NoArgsConstructor
public class TypeMvtIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String libelle;
}
