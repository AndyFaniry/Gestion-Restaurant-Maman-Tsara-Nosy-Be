package com.gestion.restaurant.entity.commandes;
import com.gestion.restaurant.entity.clients.Clients;
import com.gestion.restaurant.entity.livraisons.ZonesLivraison;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "commandes")
@Data
@NoArgsConstructor
public class Commandes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idclient", nullable = false)
    private Clients client;

    @Column(name = "datecommande", nullable = false)
    private LocalDate dateCommande;

    @ManyToOne
    @JoinColumn(name = "idzonelivraison", nullable = false)
    private ZonesLivraison zoneLivraison;

    @Column(name = "montanttotal", nullable = false, precision = 16, scale = 3)
    private BigDecimal montantTotal;
}
