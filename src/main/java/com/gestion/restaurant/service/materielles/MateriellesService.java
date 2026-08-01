package com.gestion.restaurant.service.materielles;

import com.gestion.restaurant.dto.materielles.*;
import com.gestion.restaurant.entity.fournisseurs.Fournisseurs;
import com.gestion.restaurant.entity.materielles.*;
import com.gestion.restaurant.exception.BusinessRuleException;
import com.gestion.restaurant.exception.ResourceNotFoundException;
import com.gestion.restaurant.repository.materielles.*;
import com.gestion.restaurant.service.caisse.CaisseService;
import com.gestion.restaurant.service.fournisseurs.FournisseursService;
import com.gestion.restaurant.specification.materielles.MateriellesSpecification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MateriellesService {

    public static final String STATUT_HORS_SERVICE   = "Hors Service";
    public static final String STATUT_EN_MAINTENANCE = "En maintenance";
    public static final String MVT_ENTREE            = "Entree";
    public static final String MVT_MAINTENANCE       = "Maintenance";
    public static final String MVT_HORS_SERVICE      = "HorsService";

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

    // ───────────────────────── Recherche Multicritère via DTO & Specification ─────────────────────────

    @Transactional(readOnly = true)
    public List<MaterielResponseDto> search(MaterielSearchCriteria criteria) {
        Specification<Materielles> spec = MateriellesSpecification.withFilters(criteria);
        return materiellesRepository.findAll(spec)
                .stream()
                .map(MaterielMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Materielles findById(Long id) {
        return materiellesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matériel introuvable avec l'ID : " + id));
    }

    @Transactional(readOnly = true)
    public MaterielResponseDto findDtoById(Long id) {
        return MaterielMapper.toDto(findById(id));
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

    // ───────────────────────── Sauvegarde / Modification ─────────────────────────

    @Transactional
    public MaterielResponseDto saveFromDto(MaterielRequestDto dto) {
        if (dto.getNom() == null || dto.getNom().isBlank()) {
            throw new BusinessRuleException("Le nom du matériel est obligatoire.");
        }
        if (dto.getIdCategorie() == null) {
            throw new BusinessRuleException("La catégorie du matériel est obligatoire.");
        }
        if (dto.getIdStatut() == null) {
            throw new BusinessRuleException("Le statut du matériel est obligatoire.");
        }

        Materielles materiel = (dto.getId() != null) ? findById(dto.getId()) : new Materielles();
        
        CategorieMaterielles categorie = categorieMateriellesRepository.findById(dto.getIdCategorie())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable avec l'ID : " + dto.getIdCategorie()));
        
        StatutMaterielles statut = statutMateriellesRepository.findById(dto.getIdStatut())
                .orElseThrow(() -> new ResourceNotFoundException("Statut introuvable avec l'ID : " + dto.getIdStatut()));

        materiel.setNom(dto.getNom());
        materiel.setCategorieMaterielles(categorie);
        materiel.setStatutMaterielles(statut);
        materiel.setDateEntree(dto.getDateEntree() != null ? dto.getDateEntree() : LocalDate.now());

        return MaterielMapper.toDto(materiellesRepository.save(materiel));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!materiellesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossible de supprimer : Matériel introuvable avec l'ID : " + id);
        }
        materiellesRepository.deleteById(id);
    }

    // ───────────────────────── Stock & Historique ─────────────────────────

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

    @Transactional(readOnly = true)
    public List<HistoriqueMaterielles> findHistorique(Long idMateriel) {
        return historiqueMateriellesRepository.findByMateriel_IdOrderByDateEntreeDesc(idMateriel);
    }

    @Transactional
    public HistoriqueMaterielles enregistrerAchat(Long idMateriel, LocalDate dateEntree,
                                                   BigDecimal quantite, BigDecimal prixAchat,
                                                   Long idFournisseur) {
        Materielles materiel = findById(idMateriel);

        if (quantite == null || quantite.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleException("La quantité achetée doit être strictement supérieure à zéro.");
        }
        if (prixAchat == null || prixAchat.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessRuleException("Le prix d'achat doit être positif ou nul.");
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

    // ───────────────────────── Maintenance & Hors Service ─────────────────────────

    @Transactional(readOnly = true)
    public List<MaintenanceMaterielles> findMaintenances(Long idMateriel) {
        return maintenanceMateriellesRepository.findByMateriel_IdOrderByDateMaintenanceDesc(idMateriel);
    }

    @Transactional
    public MaintenanceMaterielles enregistrerMaintenance(Long idMateriel, LocalDate dateMaintenance,
                                                           String description, BigDecimal cout,
                                                           String technicien) {
        Materielles materiel = findById(idMateriel);

        if (description == null || description.isBlank()) {
            throw new BusinessRuleException("La description de la maintenance est obligatoire.");
        }
        if (cout == null || cout.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessRuleException("Le coût de la maintenance doit être positif ou nul.");
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

    @Transactional
    public Materielles mettreHorsService(Long idMateriel) {
        Materielles materiel = findById(idMateriel);
        LocalDate date = LocalDate.now();

        BigDecimal stockActuel = getStockActuel(idMateriel);
        BigDecimal quantiteSortie = stockActuel.compareTo(BigDecimal.ZERO) > 0 ? stockActuel : BigDecimal.ONE;

        materiel.setStatutMaterielles(findOrCreateStatut(STATUT_HORS_SERVICE));
        Materielles enregistre = materiellesRepository.save(materiel);

        enregistrerMouvement(enregistre, MVT_HORS_SERVICE, quantiteSortie, date);

        return enregistre;
    }

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