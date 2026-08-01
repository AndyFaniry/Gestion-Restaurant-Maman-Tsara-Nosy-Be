package com.gestion.restaurant.service.materielles;

import com.gestion.restaurant.entity.fournisseurs.Fournisseurs;
import com.gestion.restaurant.entity.materielles.*;
import com.gestion.restaurant.repository.materielles.*;
import com.gestion.restaurant.service.caisse.CaisseService;
import com.gestion.restaurant.service.fournisseurs.FournisseursService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MateriellesService {

    public static final String STATUT_HORS_SERVICE   = "Hors Service";
    public static final String STATUT_EN_MAINTENANCE = "En maintenance";
    public static final String MVT_ENTREE       = "Entree";
    public static final String MVT_MAINTENANCE  = "Maintenance";
    public static final String MVT_HORS_SERVICE = "HorsService";

    private final MateriellesRepository materiellesRepository;
    private final CategorieMateriellesRepository categorieMateriellesRepository;
    private final StatutMateriellesRepository statutMateriellesRepository;
    private final HistoriqueMateriellesRepository historiqueMateriellesRepository;
    private final MaintenanceMateriellesRepository maintenanceMateriellesRepository;
    private final InventairesMateriellesRepository inventairesMateriellesRepository;
    private final EtatStockMateriellesRepository etatStockMateriellesRepository;
    private final TypeMvtMateriellesRepository typeMvtMateriellesRepository;
    private final CaisseService caisseService;
    private final FournisseursService fournisseursService;

    public MateriellesService(MateriellesRepository materiellesRepository,
                               CategorieMateriellesRepository categorieMateriellesRepository,
                               StatutMateriellesRepository statutMateriellesRepository,
                               HistoriqueMateriellesRepository historiqueMateriellesRepository,
                               MaintenanceMateriellesRepository maintenanceMateriellesRepository,
                               InventairesMateriellesRepository inventairesMateriellesRepository,
                               EtatStockMateriellesRepository etatStockMateriellesRepository,
                               TypeMvtMateriellesRepository typeMvtMateriellesRepository,
                               CaisseService caisseService,
                               FournisseursService fournisseursService) {
        this.materiellesRepository = materiellesRepository;
        this.categorieMateriellesRepository = categorieMateriellesRepository;
        this.statutMateriellesRepository = statutMateriellesRepository;
        this.historiqueMateriellesRepository = historiqueMateriellesRepository;
        this.maintenanceMateriellesRepository = maintenanceMateriellesRepository;
        this.inventairesMateriellesRepository = inventairesMateriellesRepository;
        this.etatStockMateriellesRepository = etatStockMateriellesRepository;
        this.typeMvtMateriellesRepository = typeMvtMateriellesRepository;
        this.caisseService = caisseService;
        this.fournisseursService = fournisseursService;
    }

    // ───────────────────────── CRUD de base ─────────────────────────

    @Transactional(readOnly = true)
    public List<Materielles> findAllFiltered(Long idCategorie, Long idStatut) {
        if (idCategorie != null && idStatut != null) {
            return materiellesRepository.findByCategorieMaterielles_IdAndStatutMaterielles_Id(idCategorie, idStatut);
        }
        if (idCategorie != null) {
            return materiellesRepository.findByCategorieMaterielles_Id(idCategorie);
        }
        if (idStatut != null) {
            return materiellesRepository.findByStatutMaterielles_Id(idStatut);
        }
        return materiellesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Materielles findById(Long id) {
        return materiellesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Materiel invalide : " + id));
    }

    @Transactional(readOnly = true)
    public List<CategorieMaterielles> findAllCategories() {
        return categorieMateriellesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<StatutMaterielles> findAllStatuts() {
        return statutMateriellesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Fournisseurs> findAllFournisseurs() {
        return fournisseursService.findAll();
    }

    @Transactional
    public Materielles save(Materielles materiel) {
        if (materiel.getNom() == null || materiel.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du matériel est obligatoire");
        }
        if (materiel.getCategorieMaterielles() == null || materiel.getCategorieMaterielles().getId() == null) {
            throw new IllegalArgumentException("La catégorie du matériel est obligatoire");
        }
        if (materiel.getStatutMaterielles() == null || materiel.getStatutMaterielles().getId() == null) {
            throw new IllegalArgumentException("Le statut du matériel est obligatoire");
        }
        if (materiel.getDateEntree() == null) {
            materiel.setDateEntree(LocalDate.now());
        }
        // Pas de mouvement de stock ici : la fiche matériel ne porte pas de quantité.
        // Le stock démarre uniquement via un achat (enregistrerAchat), comme pour les ingrédients.
        return materiellesRepository.save(materiel);
    }

    @Transactional
    public void deleteById(Long id) {
        materiellesRepository.deleteById(id);
    }

    // ───────────────────────── Stock courant (EtatStockMaterielles) ─────────────────────────

    @Transactional(readOnly = true)
    public BigDecimal getStockActuel(Long idMateriel) {
        return etatStockMateriellesRepository.findTopByMateriel_IdOrderByDateEtatStockDescIdDesc(idMateriel)
                .map(EtatStockMaterielles::getQuantite)
                .orElse(BigDecimal.ZERO);
    }

    private void enregistrerSnapshotStock(Materielles materiel, BigDecimal nouvelleQuantite, LocalDate date) {
        EtatStockMaterielles snapshot = new EtatStockMaterielles();
        snapshot.setMateriel(materiel);
        snapshot.setDateEtatStock(date != null ? date : LocalDate.now());
        snapshot.setQuantite(nouvelleQuantite);
        etatStockMateriellesRepository.save(snapshot);
    }

    // ───────────────────────── Historique des achats (suivi prix) ─────────────────────────

    @Transactional(readOnly = true)
    public List<HistoriqueMaterielles> findHistorique(Long idMateriel) {
        return historiqueMateriellesRepository.findByMateriel_IdOrderByDateEntreeDesc(idMateriel);
    }

    /**
     * Enregistre un achat de matériel : trace le prix payé (HistoriqueMaterielles),
     * journalise le mouvement (InventairesMaterielles), met à jour le stock courant
     * (EtatStockMaterielles) et génère automatiquement une sortie de caisse
     * (MouvementCaisse :: Sortie) pour quantite * prixAchat.
     */
    @Transactional
    public HistoriqueMaterielles enregistrerAchat(Long idMateriel, LocalDate dateEntree,
                                                   BigDecimal quantite, BigDecimal prixAchat,
                                                   Long idFournisseur) {
        Materielles materiel = findById(idMateriel);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La quantité achetée doit être positive");
        }
        if (prixAchat == null || prixAchat.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le prix d'achat doit être positif ou nul");
        }
        LocalDate date = dateEntree != null ? dateEntree : LocalDate.now();

        HistoriqueMaterielles historique = new HistoriqueMaterielles();
        historique.setMateriel(materiel);
        historique.setDateEntree(date);
        historique.setQuantite(quantite);
        historique.setPrixAchat(prixAchat);
        if (idFournisseur != null) {
            historique.setFournisseur(fournisseursService.findById(idFournisseur));
        }
        HistoriqueMaterielles enregistre = historiqueMateriellesRepository.save(historique);

        enregistrerMouvement(materiel, MVT_ENTREE, quantite, date);

        BigDecimal nouveauStock = getStockActuel(idMateriel).add(quantite);
        enregistrerSnapshotStock(materiel, nouveauStock, date);

        BigDecimal montantTotal = quantite.multiply(prixAchat);
        if (montantTotal.compareTo(BigDecimal.ZERO) > 0) {
            caisseService.enregistrerSortie(montantTotal, date);
        }

        return enregistre;
    }

    // ───────────────────────── Maintenance ─────────────────────────

    @Transactional(readOnly = true)
    public List<MaintenanceMaterielles> findMaintenances(Long idMateriel) {
        return maintenanceMateriellesRepository.findByMateriel_IdOrderByDateMaintenanceDesc(idMateriel);
    }

    /**
     * Enregistre une opération de maintenance : passe le matériel en statut
     * "En maintenance", journalise le mouvement (quantité symbolique = 1,
     * la maintenance ne modifie pas le stock physique) et génère
     * automatiquement une sortie de caisse pour le coût de l'intervention.
     */
    @Transactional
    public MaintenanceMaterielles enregistrerMaintenance(Long idMateriel, LocalDate dateMaintenance,
                                                           String description, BigDecimal cout,
                                                           String technicien) {
        Materielles materiel = findById(idMateriel);

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("La description de la maintenance est obligatoire");
        }
        if (cout == null || cout.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le coût de la maintenance doit être positif ou nul");
        }
        LocalDate date = dateMaintenance != null ? dateMaintenance : LocalDate.now();

        MaintenanceMaterielles maintenance = new MaintenanceMaterielles();
        maintenance.setMateriel(materiel);
        maintenance.setDateMaintenance(date);
        maintenance.setDescription(description);
        maintenance.setCout(cout);
        maintenance.setTechnicien(technicien);
        MaintenanceMaterielles enregistre = maintenanceMateriellesRepository.save(maintenance);

        materiel.setStatutMaterielles(findOrCreateStatut(STATUT_EN_MAINTENANCE));
        materiellesRepository.save(materiel);

        enregistrerMouvement(materiel, MVT_MAINTENANCE, BigDecimal.ONE, date);

        if (cout.compareTo(BigDecimal.ZERO) > 0) {
            caisseService.enregistrerSortie(cout, date);
        }

        return enregistre;
    }

    // ───────────────────────── Mise hors service ─────────────────────────

    /**
     * Déclare le matériel hors service : journalise le mouvement (la quantité
     * sortie = le stock actuellement détenu) et remet le stock courant à 0.
     */
/**
     * Déclare le matériel hors service : journalise le mouvement de sortie
     * (quantité = le stock actuellement détenu, garantie > 0). On n'insère
     * PAS de photo de stock à 0 dans EtatStockMaterielles, car cette table
     * impose CHECK(quantite > 0) — l'absence de nouvelle photo signifie
     * "plus de mouvement depuis la dernière entrée", et le statut du
     * matériel (Hors service) fait foi pour l'affichage.
     */
    @Transactional
    public Materielles mettreHorsService(Long idMateriel) {
        Materielles materiel = findById(idMateriel);
        LocalDate date = LocalDate.now();

        BigDecimal stockActuel = getStockActuel(idMateriel);
        BigDecimal quantiteSortie = stockActuel.compareTo(BigDecimal.ZERO) > 0 ? stockActuel : BigDecimal.ONE;

        materiel.setStatutMaterielles(findOrCreateStatut(STATUT_HORS_SERVICE));
        Materielles enregistre = materiellesRepository.save(materiel);

        enregistrerMouvement(enregistre, MVT_HORS_SERVICE, quantiteSortie, date);
        // Pas de enregistrerSnapshotStock(..., ZERO, ...) ici : contrainte CHECK(quantite > 0)

        return enregistre;
    }

    // ───────────────────────── Inventaire (journal des mouvements) ─────────────────────────

    @Transactional(readOnly = true)
    public List<InventairesMaterielles> findInventaire(Long idMateriel) {
        return inventairesMateriellesRepository.findByMateriel_IdOrderByDateInventaireDesc(idMateriel);
    }

    private void enregistrerMouvement(Materielles materiel, String typeLibelle, BigDecimal quantite, LocalDate date) {
        InventairesMaterielles mouvement = new InventairesMaterielles();
        mouvement.setMateriel(materiel);
        mouvement.setDateInventaire(date != null ? date : LocalDate.now());
        mouvement.setQuantite(quantite);
        mouvement.setTypeMvtMaterielles(findOrCreateTypeMvt(typeLibelle));
        inventairesMateriellesRepository.save(mouvement);
    }

    private StatutMaterielles findOrCreateStatut(String libelle) {
        return statutMateriellesRepository.findByLibelle(libelle)
                .orElseGet(() -> {
                    StatutMaterielles s = new StatutMaterielles();
                    s.setLibelle(libelle);
                    return statutMateriellesRepository.save(s);
                });
    }

    private TypeMvtMaterielles findOrCreateTypeMvt(String libelle) {
        return typeMvtMateriellesRepository.findByLibelle(libelle)
                .orElseGet(() -> {
                    TypeMvtMaterielles t = new TypeMvtMaterielles();
                    t.setLibelle(libelle);
                    return typeMvtMateriellesRepository.save(t);
                });
    }
}