package com.gestion.restaurant.entity.materielles;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "materielles")
@Data
@NoArgsConstructor
public class Materielles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(name = "dateentree", nullable = false)
    private LocalDate dateEntree;

    @ManyToOne
    @JoinColumn(name = "idcategoriematerielles", nullable = false)
    private CategorieMaterielles categorieMaterielles;

    @ManyToOne
    @JoinColumn(name = "idstatutmaterielles", nullable = false)
    private StatutMaterielles statutMaterielles;
}
