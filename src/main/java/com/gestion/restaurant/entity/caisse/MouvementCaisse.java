package com.gestion.restaurant.entity.caisse;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "mouvementcaisse")
@Data
@NoArgsConstructor
public class MouvementCaisse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datemouvement", nullable = false)
    private LocalDate dateMouvement;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal montant;

    @ManyToOne
    @JoinColumn(name = "typemouvement", nullable = false)
    private TypeMouvementCaisse typeMouvement;
}
