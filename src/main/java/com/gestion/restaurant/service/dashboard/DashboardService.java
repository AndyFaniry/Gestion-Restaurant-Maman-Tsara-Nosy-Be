package com.gestion.restaurant.service.dashboard;

import com.gestion.restaurant.repository.clients.ClientsRepository;
import com.gestion.restaurant.repository.commandes.CommandesRepository;
import com.gestion.restaurant.repository.ingredients.IngredientsRepository;
import com.gestion.restaurant.repository.personnels.PersonnelsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

    private final CommandesRepository commandesRepository;
    private final ClientsRepository clientsRepository;
    private final IngredientsRepository ingredientsRepository;
    private final PersonnelsRepository personnelsRepository;

    public DashboardService(CommandesRepository commandesRepository,
                            ClientsRepository clientsRepository,
                            IngredientsRepository ingredientsRepository,
                            PersonnelsRepository personnelsRepository) {
        this.commandesRepository = commandesRepository;
        this.clientsRepository = clientsRepository;
        this.ingredientsRepository = ingredientsRepository;
        this.personnelsRepository = personnelsRepository;
    }

    @Transactional(readOnly = true)
    public DashboardStats getStats() {
        return new DashboardStats(
                commandesRepository.count(),
                clientsRepository.count(),
                ingredientsRepository.count(),
                personnelsRepository.count()
        );
    }

    public record DashboardStats(long totalCommandes,
                                 long totalClients,
                                 long totalIngredients,
                                 long totalPersonnels) {
    }
}
