package com.gestion.restaurant.entity.commandes;
import com.gestion.restaurant.entity.plats.Plats;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "detailscommandes")
@Data
@NoArgsConstructor
public class DetailsCommandes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idcommande", nullable = false)
    private Commandes commande;

    @ManyToOne
    @JoinColumn(name = "idplat", nullable = false)
    private Plats plat;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal quantite;

    @Column(name = "prixunitaire", nullable = false, precision = 16, scale = 3)
    private BigDecimal prixUnitaire;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal montant;
}
