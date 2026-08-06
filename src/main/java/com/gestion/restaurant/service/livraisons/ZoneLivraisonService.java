package com.gestion.restaurant.service.livraisons;

import com.gestion.restaurant.dto.livraisons.ZoneLivraisonDto;
import com.gestion.restaurant.dto.livraisons.ZoneLivraisonFilterDto;
import com.gestion.restaurant.entity.livraisons.ZonesLivraison;
import com.gestion.restaurant.exception.BusinessRuleException;
import com.gestion.restaurant.exception.ResourceNotFoundException;
import com.gestion.restaurant.repository.livraisons.ZoneLivraisonRepository;
import com.gestion.restaurant.specification.livraisons.ZoneLivraisonSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZoneLivraisonService {

    private final ZoneLivraisonRepository repository;

    public ZoneLivraisonService(ZoneLivraisonRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<ZonesLivraison> findAll(ZoneLivraisonFilterDto filter, Pageable pageable) {
        return repository.findAll(ZoneLivraisonSpecification.getSpecifications(filter), pageable);
    }

    @Transactional(readOnly = true)
    public ZonesLivraison findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone de livraison non trouvée avec l'ID : " + id));
    }

    @Transactional
    public void save(ZoneLivraisonDto dto) {
        if (dto.getId() == null && repository.existsByLibelleIgnoreCase(dto.getLibelle())) {
            throw new BusinessRuleException("Une zone de livraison avec ce libellé existe déjà.", "/zones-livraison");
        }
        if (dto.getId() != null && repository.existsByLibelleIgnoreCaseAndIdNot(dto.getLibelle(), dto.getId())) {
            throw new BusinessRuleException("Une zone de livraison avec ce libellé existe déjà.", "/zones-livraison");
        }
        if (dto.getMin() != null && dto.getMax() != null && dto.getMin().compareTo(dto.getMax()) > 0) {
            throw new BusinessRuleException("La distance minimale ne peut pas dépasser la distance maximale.", "/zones-livraison");
        }

        ZonesLivraison zone = (dto.getId() != null) ? findById(dto.getId()) : new ZonesLivraison();
        zone.setLibelle(dto.getLibelle());
        zone.setMin(dto.getMin());
        zone.setMax(dto.getMax());
        zone.setPrix(dto.getPrix());

        repository.save(zone);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Zone de livraison non trouvée avec l'ID : " + id);
        }
        repository.deleteById(id);
    }

    public ZoneLivraisonDto toDto(ZonesLivraison entity) {
        ZoneLivraisonDto dto = new ZoneLivraisonDto();
        dto.setId(entity.getId());
        dto.setLibelle(entity.getLibelle());
        dto.setMin(entity.getMin());
        dto.setMax(entity.getMax());
        dto.setPrix(entity.getPrix());
        return dto;
    }
}
