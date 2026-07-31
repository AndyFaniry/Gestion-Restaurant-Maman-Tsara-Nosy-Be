package com.gestion.restaurant.entity.materielles;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "inventairematerielles")
@Data
@NoArgsConstructor
public class InventaireMaterielles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idmateriel", nullable = false)
    private Materielles materiel;

    @Column(name = "datemouvement", nullable = false)
    private LocalDate dateMouvement;

    @ManyToOne
    @JoinColumn(name = "typemvtmaterielles", nullable = false)
    private TypeMvtMaterielles typeMvtMaterielles;

    @Column(length = 255)
    private String commentaire;
}