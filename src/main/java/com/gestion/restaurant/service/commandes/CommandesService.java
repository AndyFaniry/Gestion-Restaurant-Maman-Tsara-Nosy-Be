package com.gestion.restaurant.service.commandes;

import com.gestion.restaurant.dto.commandes.CommandeCreateRequestDto;
import com.gestion.restaurant.dto.commandes.CommandeLigneRequestDto;
import com.gestion.restaurant.entity.clients.Clients;
import com.gestion.restaurant.entity.commandes.Commandes;
import com.gestion.restaurant.entity.commandes.DetailsCommandes;
import com.gestion.restaurant.entity.commandes.FacturesCommandes;
import com.gestion.restaurant.entity.ingredients.Ingredients;
import com.gestion.restaurant.entity.livraisons.ZonesLivraison;
import com.gestion.restaurant.entity.plats.Plats;
import com.gestion.restaurant.entity.plats.RecettePlats;
import com.gestion.restaurant.exception.BusinessRuleException;
import com.gestion.restaurant.exception.ResourceNotFoundException;
import com.gestion.restaurant.repository.clients.ClientsRepository;
import com.gestion.restaurant.repository.commandes.CommandesRepository;
import com.gestion.restaurant.repository.commandes.DetailsCommandesRepository;
import com.gestion.restaurant.repository.commandes.FacturesCommandesRepository;
import com.gestion.restaurant.repository.livraisons.ZoneLivraisonRepository;
import com.gestion.restaurant.repository.plats.PlatsRepository;
import com.gestion.restaurant.repository.recettes.RecettePlatsRepository;
import com.gestion.restaurant.service.caisse.CaisseService;
import com.gestion.restaurant.service.ingredients.IngredientsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class CommandesService {

    private final CommandesRepository commandesRepository;
    private final DetailsCommandesRepository detailsCommandesRepository;
    private final FacturesCommandesRepository facturesCommandesRepository;
    private final ClientsRepository clientsRepository;
    private final ZoneLivraisonRepository zoneLivraisonRepository;
    private final PlatsRepository platsRepository;
    private final RecettePlatsRepository recettePlatsRepository;
    private final CaisseService caisseService;
    private final IngredientsService ingredientsService;

    public CommandesService(CommandesRepository commandesRepository,
                            DetailsCommandesRepository detailsCommandesRepository,
                            FacturesCommandesRepository facturesCommandesRepository,
                            ClientsRepository clientsRepository,
                            ZoneLivraisonRepository zoneLivraisonRepository,
                            PlatsRepository platsRepository,
                            RecettePlatsRepository recettePlatsRepository,
                            CaisseService caisseService,
                            IngredientsService ingredientsService) {
        this.commandesRepository = commandesRepository;
        this.detailsCommandesRepository = detailsCommandesRepository;
        this.facturesCommandesRepository = facturesCommandesRepository;
        this.clientsRepository = clientsRepository;
        this.zoneLivraisonRepository = zoneLivraisonRepository;
        this.platsRepository = platsRepository;
        this.recettePlatsRepository = recettePlatsRepository;
        this.caisseService = caisseService;
        this.ingredientsService = ingredientsService;
    }

    @Transactional(readOnly = true)
    public List<Commandes> findAll() {
        return commandesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Commandes findById(Long id) {
        return commandesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande introuvable avec l'ID : " + id));
    }

    @Transactional(readOnly = true)
    public List<DetailsCommandes> findDetailsByCommandeId(Long commandeId) {
        return detailsCommandesRepository.findByCommandeId(commandeId);
    }

    @Transactional
    public Commandes creereOuMettreAJourCommande(CommandeCreateRequestDto dto) {
        if (dto.getIdClient() == null) {
            throw new BusinessRuleException("Le client est obligatoire.");
        }
        if (dto.getIdZoneLivraison() == null) {
            throw new BusinessRuleException("La zone de livraison est obligatoire.");
        }
        if (dto.getLignes() == null || dto.getLignes().isEmpty()) {
            throw new BusinessRuleException("La commande doit contenir au moins un plat.");
        }

        Clients client = clientsRepository.findById(dto.getIdClient())
                .orElseThrow(() -> new ResourceNotFoundException("Client introuvable : " + dto.getIdClient()));

        ZonesLivraison zone = zoneLivraisonRepository.findById(dto.getIdZoneLivraison())
                .orElseThrow(() -> new ResourceNotFoundException("Zone de livraison introuvable : " + dto.getIdZoneLivraison()));

        LocalDate dateMvt = dto.getDateCommande() != null ? dto.getDateCommande() : LocalDate.now();

        Commandes commande;
        if (dto.getId() != null) {
            commande = findById(dto.getId());
            detailsCommandesRepository.deleteByCommandeId(commande.getId());
        } else {
            commande = new Commandes();
        }

        commande.setClient(client);
        commande.setZoneLivraison(zone);
        commande.setDateCommande(dateMvt);

        BigDecimal totalCumule = zone.getPrix();
        commande.setMontantTotal(totalCumule);
        Commandes commandeSauvegardee = commandesRepository.save(commande);

        for (CommandeLigneRequestDto ligneDto : dto.getLignes()) {
            if (ligneDto.getIdPlat() != null && ligneDto.getQuantite() != null && ligneDto.getQuantite().compareTo(BigDecimal.ZERO) > 0) {
                Plats plat = platsRepository.findById(ligneDto.getIdPlat())
                        .orElseThrow(() -> new ResourceNotFoundException("Plat introuvable : " + ligneDto.getIdPlat()));

                BigDecimal montantLigne = plat.getPrixVente().multiply(ligneDto.getQuantite());

                DetailsCommandes detail = new DetailsCommandes();
                detail.setCommande(commandeSauvegardee);
                detail.setPlat(plat);
                detail.setQuantite(ligneDto.getQuantite());
                detail.setPrixUnitaire(plat.getPrixVente());
                detail.setMontant(montantLigne);

                detailsCommandesRepository.save(detail);

                totalCumule = totalCumule.add(montantLigne);

                // Déstockage automatique des ingrédients via les recettes
                List<RecettePlats> recettes = recettePlatsRepository.findByPlatId(plat.getId());
                for (RecettePlats rp : recettes) {
                    Ingredients ingredient = rp.getIngredient();
                    BigDecimal quantiteIngredientTotale = rp.getQuantiteRequise().multiply(ligneDto.getQuantite());
                    
                    ingredientsService.enregistrerSortieOuPerte(
                            ingredient.getId(),
                            quantiteIngredientTotale,
                            "Sortie",
                            dateMvt
                    );
                }
            }
        }

        commandeSauvegardee.setMontantTotal(totalCumule);
        Commandes commandeFinale = commandesRepository.save(commandeSauvegardee);

        // Enregistrement de la facture et entrée en caisse
        FacturesCommandes facture = new FacturesCommandes();
        facture.setCommande(commandeFinale);
        facture.setDateFacture(dateMvt);
        facture.setMontantTotal(totalCumule);
        facturesCommandesRepository.save(facture);

        caisseService.enregistrerEntree(totalCumule, dateMvt);

        return commandeFinale;
    }

    @Transactional
    public void deleteById(Long id) {
        if (!commandesRepository.existsById(id)) {
            throw new ResourceNotFoundException("Commande introuvable avec l'ID : " + id);
        }
        detailsCommandesRepository.deleteByCommandeId(id);
        commandesRepository.deleteById(id);
    }
}