package com.gestion.restaurant.entity.materielles;

import com.gestion.restaurant.entity.fournisseurs.Fournisseurs;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "historiquematerielles")
@Data
@NoArgsConstructor
public class HistoriqueMaterielles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idmaterielles", nullable = false)
    private Materielles materiel;

    @Column(name = "dateentree", nullable = false)
    private LocalDate dateEntree;

    @Column(name = "prixachat", nullable = false, precision = 16, scale = 3)
    private BigDecimal prixAchat;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal quantite;

    @ManyToOne
    @JoinColumn(name = "idfournisseur")
    private Fournisseurs fournisseur; // nullable, comme dans le script SQL
}