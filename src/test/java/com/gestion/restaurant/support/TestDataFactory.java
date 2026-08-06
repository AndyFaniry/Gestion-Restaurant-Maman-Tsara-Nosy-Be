package com.gestion.restaurant.support;

import com.gestion.restaurant.entity.caisse.TypeMouvementCaisse;
import com.gestion.restaurant.entity.clients.Clients;
import com.gestion.restaurant.entity.clients.TypeClient;
import com.gestion.restaurant.entity.fournisseurs.Fournisseurs;
import com.gestion.restaurant.entity.fournisseurs.TypeFournisseurs;
import com.gestion.restaurant.entity.ingredients.*;
import com.gestion.restaurant.entity.livraisons.ZonesLivraison;
import com.gestion.restaurant.entity.materielles.CategorieMaterielles;
import com.gestion.restaurant.entity.materielles.Materielles;
import com.gestion.restaurant.entity.materielles.StatutMaterielles;
import com.gestion.restaurant.entity.personnels.Personnels;
import com.gestion.restaurant.entity.personnels.RaisonAbsence;
import com.gestion.restaurant.entity.personnels.RolePersonnels;
import com.gestion.restaurant.entity.plats.CategoriePlats;
import com.gestion.restaurant.entity.plats.Plats;
import com.gestion.restaurant.entity.plats.RecettePlats;
import com.gestion.restaurant.repository.caisse.TypeMouvementCaisseRepository;
import com.gestion.restaurant.repository.clients.ClientsRepository;
import com.gestion.restaurant.repository.clients.TypeClientRepository;
import com.gestion.restaurant.repository.fournisseur.FournisseursRepository;
import com.gestion.restaurant.repository.fournisseur.TypeFournisseursRepository;
import com.gestion.restaurant.repository.ingredients.*;
import com.gestion.restaurant.repository.livraisons.ZoneLivraisonRepository;
import com.gestion.restaurant.repository.materielles.CategorieMateriellesRepository;
import com.gestion.restaurant.repository.materielles.MateriellesRepository;
import com.gestion.restaurant.repository.materielles.StatutMateriellesRepository;
import com.gestion.restaurant.repository.personnels.PersonnelsRepository;
import com.gestion.restaurant.repository.personnels.RaisonAbsenceRepository;
import com.gestion.restaurant.repository.personnels.RolePersonnelsRepository;
import com.gestion.restaurant.repository.plats.CategoriePlatsRepository;
import com.gestion.restaurant.repository.plats.PlatsRepository;
import com.gestion.restaurant.repository.recettes.RecettePlatsRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Factory de données métier pour les tests d'intégration / fonctionnels.
 */
@Component
public class TestDataFactory {

    private final TypeClientRepository typeClientRepository;
    private final ClientsRepository clientsRepository;
    private final TypeFournisseursRepository typeFournisseursRepository;
    private final FournisseursRepository fournisseursRepository;
    private final UniteRepository uniteRepository;
    private final CategorieIngredientsRepository categorieIngredientsRepository;
    private final StatutIngredientRepository statutIngredientRepository;
    private final IngredientsRepository ingredientsRepository;
    private final EtatStockIngredientRepository etatStockIngredientRepository;
    private final TypeMvtIngredientRepository typeMvtIngredientRepository;
    private final TypeMouvementCaisseRepository typeMouvementCaisseRepository;
    private final ZoneLivraisonRepository zoneLivraisonRepository;
    private final CategoriePlatsRepository categoriePlatsRepository;
    private final PlatsRepository platsRepository;
    private final RecettePlatsRepository recettePlatsRepository;
    private final RolePersonnelsRepository rolePersonnelsRepository;
    private final PersonnelsRepository personnelsRepository;
    private final RaisonAbsenceRepository raisonAbsenceRepository;
    private final CategorieMateriellesRepository categorieMateriellesRepository;
    private final StatutMateriellesRepository statutMateriellesRepository;
    private final MateriellesRepository materiellesRepository;

    public TestDataFactory(TypeClientRepository typeClientRepository,
                           ClientsRepository clientsRepository,
                           TypeFournisseursRepository typeFournisseursRepository,
                           FournisseursRepository fournisseursRepository,
                           UniteRepository uniteRepository,
                           CategorieIngredientsRepository categorieIngredientsRepository,
                           StatutIngredientRepository statutIngredientRepository,
                           IngredientsRepository ingredientsRepository,
                           EtatStockIngredientRepository etatStockIngredientRepository,
                           TypeMvtIngredientRepository typeMvtIngredientRepository,
                           TypeMouvementCaisseRepository typeMouvementCaisseRepository,
                           ZoneLivraisonRepository zoneLivraisonRepository,
                           CategoriePlatsRepository categoriePlatsRepository,
                           PlatsRepository platsRepository,
                           RecettePlatsRepository recettePlatsRepository,
                           RolePersonnelsRepository rolePersonnelsRepository,
                           PersonnelsRepository personnelsRepository,
                           RaisonAbsenceRepository raisonAbsenceRepository,
                           CategorieMateriellesRepository categorieMateriellesRepository,
                           StatutMateriellesRepository statutMateriellesRepository,
                           MateriellesRepository materiellesRepository) {
        this.typeClientRepository = typeClientRepository;
        this.clientsRepository = clientsRepository;
        this.typeFournisseursRepository = typeFournisseursRepository;
        this.fournisseursRepository = fournisseursRepository;
        this.uniteRepository = uniteRepository;
        this.categorieIngredientsRepository = categorieIngredientsRepository;
        this.statutIngredientRepository = statutIngredientRepository;
        this.ingredientsRepository = ingredientsRepository;
        this.etatStockIngredientRepository = etatStockIngredientRepository;
        this.typeMvtIngredientRepository = typeMvtIngredientRepository;
        this.typeMouvementCaisseRepository = typeMouvementCaisseRepository;
        this.zoneLivraisonRepository = zoneLivraisonRepository;
        this.categoriePlatsRepository = categoriePlatsRepository;
        this.platsRepository = platsRepository;
        this.recettePlatsRepository = recettePlatsRepository;
        this.rolePersonnelsRepository = rolePersonnelsRepository;
        this.personnelsRepository = personnelsRepository;
        this.raisonAbsenceRepository = raisonAbsenceRepository;
        this.categorieMateriellesRepository = categorieMateriellesRepository;
        this.statutMateriellesRepository = statutMateriellesRepository;
        this.materiellesRepository = materiellesRepository;
    }

    @Transactional
    public void ensureLookups() {
        if (typeMouvementCaisseRepository.findByLibelle("Entree").isEmpty()) {
            typeMouvementCaisseRepository.save(typeCaisse("Entree"));
            typeMouvementCaisseRepository.save(typeCaisse("Sortie"));
        }
        if (typeMvtIngredientRepository.findByLibelleIgnoreCase("Entrée").isEmpty()) {
            typeMvtIngredientRepository.save(typeMvtIng("Entrée"));
            typeMvtIngredientRepository.save(typeMvtIng("Sortie (Cuisine)"));
            typeMvtIngredientRepository.save(typeMvtIng("Perte / Périmé"));
        }
        if (statutMateriellesRepository.findByLibelle("En service").isEmpty()) {
            statutMateriellesRepository.save(statutMat("En service"));
            statutMateriellesRepository.save(statutMat("En maintenance"));
            statutMateriellesRepository.save(statutMat("Hors Service"));
        }
    }

    public TypeClient typeClient(String libelle) {
        TypeClient t = new TypeClient();
        t.setLibelle(libelle);
        return typeClientRepository.save(t);
    }

    public Clients client(String nom, TypeClient type) {
        Clients c = new Clients();
        c.setNom(nom);
        c.setPrenom("Test");
        c.setContact("0320000000");
        c.setTypeClient(type);
        return clientsRepository.save(c);
    }

    public TypeFournisseurs typeFournisseur(String libelle) {
        TypeFournisseurs t = new TypeFournisseurs();
        t.setLibelle(libelle);
        return typeFournisseursRepository.save(t);
    }

    public Fournisseurs fournisseur(String nom, TypeFournisseurs type) {
        Fournisseurs f = new Fournisseurs();
        f.setNom(nom);
        f.setPrenom("Fourn");
        f.setContact("0330000000");
        f.setTypeFournisseurs(type);
        return fournisseursRepository.save(f);
    }

    public Unite unite(String nom, String symbole) {
        Unite u = new Unite();
        u.setNom(nom);
        u.setSymbole(symbole);
        return uniteRepository.save(u);
    }

    public CategorieIngredients categorieIngredient(String libelle) {
        CategorieIngredients c = new CategorieIngredients();
        c.setLibelle(libelle);
        return categorieIngredientsRepository.save(c);
    }

    public StatutIngredient statutIngredient(String libelle) {
        StatutIngredient s = new StatutIngredient();
        s.setLibelle(libelle);
        return statutIngredientRepository.save(s);
    }

    public Ingredients ingredient(String nom, CategorieIngredients cat, StatutIngredient statut,
                                  Fournisseurs fournisseur, Unite unite) {
        Ingredients i = new Ingredients();
        i.setNom(nom);
        i.setCategorieIngredients(cat);
        i.setStatutIngredient(statut);
        i.setFournisseur(fournisseur);
        i.setUnite(unite);
        return ingredientsRepository.save(i);
    }

    public void avecStock(Ingredients ingredient, BigDecimal quantite) {
        EtatStockIngredient etat = new EtatStockIngredient();
        etat.setIngredient(ingredient);
        etat.setDateEtatStock(LocalDate.now());
        etat.setQuantite(quantite);
        etatStockIngredientRepository.save(etat);
    }

    public ZonesLivraison zone(String libelle, BigDecimal prix) {
        ZonesLivraison z = new ZonesLivraison();
        z.setLibelle(libelle);
        z.setMin(BigDecimal.ZERO);
        z.setMax(new BigDecimal("10"));
        z.setPrix(prix);
        return zoneLivraisonRepository.save(z);
    }

    public CategoriePlats categoriePlat(String libelle) {
        CategoriePlats c = new CategoriePlats();
        c.setLibelle(libelle);
        return categoriePlatsRepository.save(c);
    }

    public Plats plat(String nom, BigDecimal prixVente, CategoriePlats cat) {
        Plats p = new Plats();
        p.setNom(nom);
        p.setPrixVente(prixVente);
        p.setCategoriePlats(cat);
        return platsRepository.save(p);
    }

    public RecettePlats recette(Plats plat, Ingredients ingredient, BigDecimal qty) {
        RecettePlats rp = new RecettePlats();
        rp.setPlat(plat);
        rp.setIngredient(ingredient);
        rp.setQuantiteRequise(qty);
        return recettePlatsRepository.save(rp);
    }

    public RolePersonnels role(String libelle) {
        RolePersonnels r = new RolePersonnels();
        r.setLibelle(libelle);
        return rolePersonnelsRepository.save(r);
    }

    public Personnels personnel(String nom, RolePersonnels role) {
        Personnels p = new Personnels();
        p.setNom(nom);
        p.setPrenom("Emp");
        p.setContact("0340000000");
        p.setDateEmbauche(LocalDate.now().minusYears(1));
        p.setRolePersonnels(role);
        return personnelsRepository.save(p);
    }

    public RaisonAbsence raisonAbsence(String libelle) {
        RaisonAbsence r = new RaisonAbsence();
        r.setLibelle(libelle);
        return raisonAbsenceRepository.save(r);
    }

    public CategorieMaterielles categorieMateriel(String libelle) {
        CategorieMaterielles c = new CategorieMaterielles();
        c.setLibelle(libelle);
        return categorieMateriellesRepository.save(c);
    }

    public StatutMaterielles statutMateriel(String libelle) {
        return statutMateriellesRepository.findByLibelle(libelle)
                .orElseGet(() -> statutMateriellesRepository.save(statutMat(libelle)));
    }

    public Materielles materiel(String nom, CategorieMaterielles cat, StatutMaterielles statut) {
        Materielles m = new Materielles();
        m.setNom(nom);
        m.setCategorieMaterielles(cat);
        m.setStatutMaterielles(statut);
        m.setDateEntree(LocalDate.now());
        return materiellesRepository.save(m);
    }

    /** Jeu minimal pour créer une commande (client + zone + plat avec recette + stock). */
    @Transactional
    public CommandeScenario commandeScenario() {
        ensureLookups();
        TypeClient typeClient = typeClient("Particulier");
        Clients client = client("Rakoto", typeClient);
        ZonesLivraison zone = zone("Hell-Ville", new BigDecimal("2000"));
        TypeFournisseurs typeF = typeFournisseur("Alimentaire");
        Fournisseurs fournisseur = fournisseur("Supplier", typeF);
        Unite unite = unite("Gramme-" + System.nanoTime(), "g");
        CategorieIngredients catIng = categorieIngredient("Viande");
        StatutIngredient statutIng = statutIngredient("Actif");
        Ingredients ing1 = ingredient("Poulet", catIng, statutIng, fournisseur, unite);
        Ingredients ing2 = ingredient("Riz", catIng, statutIng, fournisseur, unite);
        avecStock(ing1, new BigDecimal("100"));
        avecStock(ing2, new BigDecimal("100"));
        CategoriePlats catPlat = categoriePlat("Principal");
        Plats plat = plat("Riz poulet", new BigDecimal("15000"), catPlat);
        recette(plat, ing1, new BigDecimal("2"));
        recette(plat, ing2, new BigDecimal("3"));
        return new CommandeScenario(client, zone, plat, ing1, ing2);
    }

    public record CommandeScenario(Clients client, ZonesLivraison zone, Plats plat,
                                   Ingredients ingredient1, Ingredients ingredient2) {
    }

    private static TypeMouvementCaisse typeCaisse(String libelle) {
        TypeMouvementCaisse t = new TypeMouvementCaisse();
        t.setLibelle(libelle);
        return t;
    }

    private static TypeMvtIngredient typeMvtIng(String libelle) {
        TypeMvtIngredient t = new TypeMvtIngredient();
        t.setLibelle(libelle);
        return t;
    }

    private static StatutMaterielles statutMat(String libelle) {
        StatutMaterielles s = new StatutMaterielles();
        s.setLibelle(libelle);
        return s;
    }
}
