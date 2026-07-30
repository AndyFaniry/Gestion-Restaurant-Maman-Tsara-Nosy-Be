package com.gestion.restaurant.entity.personnels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fichepaie")
@Data
@NoArgsConstructor
public class FichePaie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idpersonnels", nullable = false)
    private Personnels personnel;

    @Column(name = "datepaie", nullable = false)
    private LocalDate datePaie;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal salaire;

    @Column(name = "montanttotal", nullable = false, precision = 16, scale = 3)
    private BigDecimal montantTotal;
}
