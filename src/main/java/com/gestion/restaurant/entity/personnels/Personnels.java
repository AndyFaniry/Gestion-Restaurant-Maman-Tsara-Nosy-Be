package com.gestion.restaurant.entity.personnels;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "personnels")
@Data
@NoArgsConstructor
public class Personnels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;

    @Column(nullable = false, length = 50)
    private String contact;

    @ManyToOne
    @JoinColumn(name = "idrolepersonnels", nullable = false)
    private RolePersonnels rolePersonnels;

    @Column(name = "dateembauche", nullable = false)
    private LocalDate dateEmbauche;
}
