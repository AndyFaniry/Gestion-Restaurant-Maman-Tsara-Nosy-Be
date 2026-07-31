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
    @JoinColumn(name = "idmateriel", nullable = false)
    private Materielles materiel;

    @Column(name = "dateachat", nullable = false)
    private LocalDate dateAchat;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal quantite;

    @Column(name = "prixachat", nullable = false, precision = 16, scale = 3)
    private BigDecimal prixAchat;

    @ManyToOne
    @JoinColumn(name = "idfournisseur")
    private Fournisseurs fournisseur; // optionnel
}