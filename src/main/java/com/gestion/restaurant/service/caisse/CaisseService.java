package com.gestion.restaurant.service.caisse;

import com.gestion.restaurant.entity.caisse.MouvementCaisse;
import com.gestion.restaurant.entity.caisse.TypeMouvementCaisse;
import com.gestion.restaurant.repository.caisse.MouvementCaisseRepository;
import com.gestion.restaurant.repository.caisse.TypeMouvementCaisseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Service central pour la caisse. Utilisé par d'autres modules (Materielles,
 * bientôt Ingredients/Commandes) pour générer automatiquement des mouvements
 * de caisse liés à leurs opérations, sans dupliquer la logique métier.
 */
@Service
public class CaisseService {

    public static final String TYPE_ENTREE = "Entree";
    public static final String TYPE_SORTIE = "Sortie";

    private final MouvementCaisseRepository mouvementCaisseRepository;
    private final TypeMouvementCaisseRepository typeMouvementCaisseRepository;

    public CaisseService(MouvementCaisseRepository mouvementCaisseRepository,
                          TypeMouvementCaisseRepository typeMouvementCaisseRepository) {
        this.mouvementCaisseRepository = mouvementCaisseRepository;
        this.typeMouvementCaisseRepository = typeMouvementCaisseRepository;
    }

    @Transactional
    public MouvementCaisse enregistrerSortie(BigDecimal montant, LocalDate date) {
        return enregistrerMouvement(TYPE_SORTIE, montant, date);
    }

    @Transactional
    public MouvementCaisse enregistrerEntree(BigDecimal montant, LocalDate date) {
        return enregistrerMouvement(TYPE_ENTREE, montant, date);
    }

    @Transactional
    public MouvementCaisse enregistrerMouvement(String typeLibelle, BigDecimal montant, LocalDate date) {
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant du mouvement de caisse doit être positif");
        }
        TypeMouvementCaisse type = typeMouvementCaisseRepository.findByLibelle(typeLibelle)
                .orElseGet(() -> {
                    TypeMouvementCaisse t = new TypeMouvementCaisse();
                    t.setLibelle(typeLibelle);
                    return typeMouvementCaisseRepository.save(t);
                });

        MouvementCaisse mouvement = new MouvementCaisse();
        mouvement.setDateMouvement(date != null ? date : LocalDate.now());
        mouvement.setMontant(montant);
        mouvement.setTypeMouvement(type);
        return mouvementCaisseRepository.save(mouvement);
    }
}