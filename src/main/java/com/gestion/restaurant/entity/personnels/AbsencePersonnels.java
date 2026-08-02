package com.gestion.restaurant.entity.personnels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "absencepersonnels")
@Data
@NoArgsConstructor
public class AbsencePersonnels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idpersonnels", nullable = false)
    private Personnels personnel;

    @Column(name = "datedebut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "datefin", nullable = false)
    private LocalDate dateFin;

    @ManyToOne
    @JoinColumn(name = "idraisonabsence", nullable = false)
    private RaisonAbsence raisonAbsence;

    @Column(length = 255)
    private String commentaire;
}