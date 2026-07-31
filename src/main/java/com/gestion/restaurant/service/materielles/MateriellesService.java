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

    // Libellés de référence, créés automatiquement en base s'ils n'existent pas encore
    public static final String STATUT_HORS_SERVICE   = "Hors service";
    public static final String STATUT_EN_MAINTENANCE = "En maintenance";
    public static final String MVT_ENTREE       = "Entree";
    public static final String MVT_MAINTENANCE  = "Maintenance";
    public static final String MVT_HORS_SERVICE = "HorsService";

    private final MateriellesRepository materiellesRepository;
    private final CategorieMateriellesRepository categorieMateriellesRepository;
    private final StatutMateriellesRepository statutMateriellesRepository;
    private final HistoriqueMateriellesRepository historiqueMateriellesRepository;
    private final MaintenanceMateriellesRepository maintenanceMateriellesRepository;
    private final InventaireMateriellesRepository inventaireMateriellesRepository;
    private final TypeMvtMateriellesRepository typeMvtMateriellesRepository;
    private final CaisseService caisseService;
    private final FournisseursService fournisseursService;

    public MateriellesService(MateriellesRepository materiellesRepository,
                               CategorieMateriellesRepository categorieMateriellesRepository,
                               StatutMateriellesRepository statutMateriellesRepository,
                               HistoriqueMateriellesRepository historiqueMateriellesRepository,
                               MaintenanceMateriellesRepository maintenanceMateriellesRepository,
                               InventaireMateriellesRepository inventaireMateriellesRepository,
                               TypeMvtMateriellesRepository typeMvtMateriellesRepository,
                               CaisseService caisseService,
                               FournisseursService fournisseursService) {
        this.materiellesRepository = materiellesRepository;
        this.categorieMateriellesRepository = categorieMateriellesRepository;
        this.statutMateriellesRepository = statutMateriellesRepository;
        this.historiqueMateriellesRepository = historiqueMateriellesRepository;
        this.maintenanceMateriellesRepository = maintenanceMateriellesRepository;
        this.inventaireMateriellesRepository = inventaireMateriellesRepository;
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

    /**
     * Crée ou met à jour la fiche du matériel. A la création, journalise
     * automatiquement une entrée dans l'inventaire (mouvement "Entree").
     */
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

        boolean estNouveau = materiel.getId() == null;
        Materielles enregistre = materiellesRepository.save(materiel);

        if (estNouveau) {
            enregistrerMouvement(enregistre, MVT_ENTREE, "Entrée en inventaire");
        }
        return enregistre;
    }

    @Transactional
    public void deleteById(Long id) {
        materiellesRepository.deleteById(id);
    }

    // ───────────────────────── Historique des achats (suivi prix) ─────────────────────────

    @Transactional(readOnly = true)
    public List<HistoriqueMaterielles> findHistorique(Long idMateriel) {
        return historiqueMateriellesRepository.findByMateriel_IdOrderByDateAchatDesc(idMateriel);
    }

    /**
     * Enregistre un achat de matériel : trace le prix payé (HistoriqueMaterielles),
     * journalise le mouvement (InventaireMaterielles) et génère automatiquement
     * une sortie de caisse (MouvementCaisse :: Sortie) pour quantite * prixAchat.
     */
    @Transactional
    public HistoriqueMaterielles enregistrerAchat(Long idMateriel, LocalDate dateAchat,
                                                   BigDecimal quantite, BigDecimal prixAchat,
                                                   Long idFournisseur) {
        Materielles materiel = findById(idMateriel);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La quantité achetée doit être positive");
        }
        if (prixAchat == null || prixAchat.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le prix d'achat doit être positif");
        }

        HistoriqueMaterielles historique = new HistoriqueMaterielles();
        historique.setMateriel(materiel);
        historique.setDateAchat(dateAchat != null ? dateAchat : LocalDate.now());
        historique.setQuantite(quantite);
        historique.setPrixAchat(prixAchat);
        if (idFournisseur != null) {
            historique.setFournisseur(fournisseursService.findById(idFournisseur));
        }
        HistoriqueMaterielles enregistre = historiqueMateriellesRepository.save(historique);

        enregistrerMouvement(materiel, MVT_ENTREE, "Achat : " + quantite + " x " + prixAchat + " Ar");

        BigDecimal montantTotal = quantite.multiply(prixAchat);
        caisseService.enregistrerSortie(montantTotal, historique.getDateAchat());

        return enregistre;
    }

    // ───────────────────────── Maintenance ─────────────────────────

    @Transactional(readOnly = true)
    public List<MaintenanceMaterielles> findMaintenances(Long idMateriel) {
        return maintenanceMateriellesRepository.findByMateriel_IdOrderByDateMaintenanceDesc(idMateriel);
    }

    /**
     * Enregistre une opération de maintenance : passe le matériel en statut
     * "En maintenance", journalise le mouvement et génère automatiquement
     * une sortie de caisse (MouvementCaisse :: Sortie) pour le coût de l'intervention.
     */
    @Transactional
    public MaintenanceMaterielles enregistrerMaintenance(Long idMateriel, LocalDate dateMaintenance,
                                                           String description, BigDecimal cout,
                                                           String technicien) {
        Materielles materiel = findById(idMateriel);

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("La description de la maintenance est obligatoire");
        }
        if (cout == null || cout.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le coût de la maintenance doit être positif");
        }

        MaintenanceMaterielles maintenance = new MaintenanceMaterielles();
        maintenance.setMateriel(materiel);
        maintenance.setDateMaintenance(dateMaintenance != null ? dateMaintenance : LocalDate.now());
        maintenance.setDescription(description);
        maintenance.setCout(cout);
        maintenance.setTechnicien(technicien);
        MaintenanceMaterielles enregistre = maintenanceMateriellesRepository.save(maintenance);

        materiel.setStatutMaterielles(findOrCreateStatut(STATUT_EN_MAINTENANCE));
        materiellesRepository.save(materiel);

        enregistrerMouvement(materiel, MVT_MAINTENANCE, description);
        caisseService.enregistrerSortie(cout, maintenance.getDateMaintenance());

        return enregistre;
    }

    // ───────────────────────── Mise hors service ─────────────────────────

    @Transactional
    public Materielles mettreHorsService(Long idMateriel) {
        Materielles materiel = findById(idMateriel);
        materiel.setStatutMaterielles(findOrCreateStatut(STATUT_HORS_SERVICE));
        Materielles enregistre = materiellesRepository.save(materiel);
        enregistrerMouvement(enregistre, MVT_HORS_SERVICE, "Matériel déclaré hors service");
        return enregistre;
    }

    // ───────────────────────── Inventaire (journal des mouvements = EtatStock) ─────────────────────────

    @Transactional(readOnly = true)
    public List<InventaireMaterielles> findInventaire(Long idMateriel) {
        return inventaireMateriellesRepository.findByMateriel_IdOrderByDateMouvementDesc(idMateriel);
    }

    private void enregistrerMouvement(Materielles materiel, String typeLibelle, String commentaire) {
        InventaireMaterielles mouvement = new InventaireMaterielles();
        mouvement.setMateriel(materiel);
        mouvement.setDateMouvement(LocalDate.now());
        mouvement.setTypeMvtMaterielles(findOrCreateTypeMvt(typeLibelle));
        mouvement.setCommentaire(commentaire);
        inventaireMateriellesRepository.save(mouvement);
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