package com.gestion.restaurant.entity.materielles;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "maintenancematerielles")
@Data
@NoArgsConstructor
public class MaintenanceMaterielles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idmaterielles")
    private Materielles materiel;

    @Column(name = "datemaintenance", nullable = false)
    private LocalDate dateMaintenance;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false, precision = 16, scale = 3)
    private BigDecimal cout;

    @Column(length = 100)
    private String technicien;
}