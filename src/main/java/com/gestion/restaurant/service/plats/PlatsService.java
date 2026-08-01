package com.gestion.restaurant.service.plats;



import com.gestion.restaurant.entity.plats.Plats;
import com.gestion.restaurant.exception.ResourceNotFoundException;
import com.gestion.restaurant.repository.plats.PlatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlatsService {

    private final PlatsRepository platsRepository;

    public PlatsService(PlatsRepository platsRepository) {
        this.platsRepository = platsRepository;
    }

    @Transactional(readOnly = true)
    public Plats findById(Long id) {
        return platsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plat introuvable avec l'ID : " + id));
    }
}