package com.gestion.restaurant.entity.plats;

import com.gestion.restaurant.entity.ingredients.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "recetteplats")
@Data
@NoArgsConstructor
public class RecettePlats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idplat", nullable = false)
    private Plats plat;

    @ManyToOne
    @JoinColumn(name = "idingredient", nullable = false)
    private Ingredients ingredient;

    @Column(name = "quantiterequise", nullable = false, precision = 16, scale = 3)
    private BigDecimal quantiteRequise;
}
