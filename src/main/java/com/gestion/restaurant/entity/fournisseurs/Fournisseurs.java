package com.gestion.restaurant.entity.fournisseurs;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fournisseurs")
@Data
@NoArgsConstructor
public class Fournisseurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "typefournisseurs", nullable = false)
    private TypeFournisseurs typeFournisseurs;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;

    @Column(nullable = false, length = 50)
    private String contact;
}
