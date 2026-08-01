package com.gestion.restaurant.service.ingredients;

import com.gestion.restaurant.dto.ingredients.AchatIngredientRequestDto;
import com.gestion.restaurant.entity.ingredients.*;
import com.gestion.restaurant.repository.ingredients.*;
import com.gestion.restaurant.service.caisse.CaisseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class StockIngredientService {

    private final HistoriqueIngredientsRepository historiqueRepository;
    private final EtatStockIngredientRepository etatStockRepository;
    private final InventaireIngredientRepository inventaireRepository;
    private final IngredientsRepository ingredientsRepository;
    private final TypeMvtIngredientRepository typeMvtIngredientRepository;
    private final CaisseService caisseService;

    public StockIngredientService(HistoriqueIngredientsRepository historiqueRepository,
                                  EtatStockIngredientRepository etatStockRepository,
                                  InventaireIngredientRepository inventaireRepository,
                                  IngredientsRepository ingredientsRepository,
                                  TypeMvtIngredientRepository typeMvtIngredientRepository,
                                  CaisseService caisseService) {
        this.historiqueRepository = historiqueRepository;
        this.etatStockRepository = etatStockRepository;
        this.inventaireRepository = inventaireRepository;
        this.ingredientsRepository = ingredientsRepository;
        this.typeMvtIngredientRepository = typeMvtIngredientRepository;
        this.caisseService = caisseService;
    }

    /**
     * 1. Enregistrer un achat d'ingrédient : Entrée en Stock + Mouvement Caisse (Sortie)
     */
    @Transactional
    public void enregistrerAchatIngredient(AchatIngredientRequestDto dto) {
        Ingredients ingredient = ingredientsRepository.findById(dto.getIdIngredient())
                .orElseThrow(() -> new IllegalArgumentException("Ingrédient introuvable ID: " + dto.getIdIngredient()));

        // A. Enregistrement dans Historique (Lot d'entrée)
        HistoriqueIngredients historique = new HistoriqueIngredients();
        historique.setIngredient(ingredient);
        historique.setQuantite(BigDecimal.valueOf(dto.getQuantite()));
        historique.setPrixAchat(BigDecimal.valueOf(dto.getPrixAchatUnitaire()));
        historique.setDateEntree(dto.getDateEntree() != null ? dto.getDateEntree() : LocalDate.now());
        historique.setDatePeremption(dto.getDatePeremption());
        historiqueRepository.save(historique);

        // B. Mise à jour de l'État du Stock Global
        EtatStockIngredient etatStock = etatStockRepository.findByIngredient_Id(ingredient.getId())
                .orElse(new EtatStockIngredient(ingredient, LocalDate.now(), BigDecimal.ZERO));
        
        etatStock.setQuantite(etatStock.getQuantite().add(BigDecimal.valueOf(dto.getQuantite())));
        etatStock.setDateEtatStock(LocalDate.now());
        etatStockRepository.save(etatStock);

        // C. Sortie d'argent en Caisse via le service dédié (Achat = Dépense)
        BigDecimal montantTotal = BigDecimal.valueOf(dto.getQuantite()).multiply(BigDecimal.valueOf(dto.getPrixAchatUnitaire()));
        caisseService.enregistrerSortie(montantTotal, LocalDate.now());
    }

    /**
     * 2. Traitement automatique et purge des ingrédients périmés
     */
    @Transactional
    public void traiterIngredientsPerimes() {
        LocalDate aujourdhui = LocalDate.now();
        List<HistoriqueIngredients> lotsPerimes = historiqueRepository.findLotsPerimes(aujourdhui);

        TypeMvtIngredient typeSortiePerime = typeMvtIngredientRepository.findByLibelle("PERIME")
                .orElseGet(() -> {
                    TypeMvtIngredient t = new TypeMvtIngredient();
                    t.setLibelle("PERIME");
                    return typeMvtIngredientRepository.save(t);
                });

        for (HistoriqueIngredients lot : lotsPerimes) {
            if (lot.getQuantite() != null && lot.getQuantite().compareTo(BigDecimal.ZERO) > 0) {
                // A. Tracer le mouvement dans l'Inventaire
                InventaireIngredient inventaire = new InventaireIngredient();
                inventaire.setIngredient(lot.getIngredient());
                inventaire.setDateInventaire(aujourdhui);
                inventaire.setQuantite(lot.getQuantite());
                inventaire.setTypeMvtIngredient(typeSortiePerime);
                inventaireRepository.save(inventaire);

                // B. Déduire du stock global
                EtatStockIngredient stock = etatStockRepository.findByIngredient_Id(lot.getIngredient().getId())
                        .orElse(null);
                if (stock != null) {
                    BigDecimal nouvelleQuantite = stock.getQuantite().subtract(lot.getQuantite());
                    if (nouvelleQuantite.compareTo(BigDecimal.ZERO) < 0) {
                        nouvelleQuantite = BigDecimal.ZERO;
                    }
                    stock.setQuantite(nouvelleQuantite);
                    stock.setDateEtatStock(aujourdhui);
                    etatStockRepository.save(stock);
                }

                // C. Marquer le lot comme consommé/purgé
                lot.setQuantite(BigDecimal.ZERO);
                historiqueRepository.save(lot);
            }
        }
    }
}