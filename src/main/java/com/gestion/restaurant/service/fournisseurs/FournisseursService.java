package com.gestion.restaurant.service.fournisseurs;

import com.gestion.restaurant.entity.fournisseurs.Fournisseurs;
import com.gestion.restaurant.entity.fournisseurs.TypeFournisseurs;
import com.gestion.restaurant.repository.fournisseur.FournisseursRepository;
import com.gestion.restaurant.repository.fournisseur.TypeFournisseursRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FournisseursService {

    private final FournisseursRepository fournisseursRepository;
    private final TypeFournisseursRepository typeFournisseursRepository;

    public FournisseursService(FournisseursRepository fournisseursRepository,
                                TypeFournisseursRepository typeFournisseursRepository) {
        this.fournisseursRepository = fournisseursRepository;
        this.typeFournisseursRepository = typeFournisseursRepository;
    }

    @Transactional(readOnly = true)
    public List<Fournisseurs> findAll() {
        return fournisseursRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Fournisseurs findById(Long id) {
        return fournisseursRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Fournisseur invalide : " + id));
    }

    @Transactional(readOnly = true)
    public List<TypeFournisseurs> findAllTypes() {
        return typeFournisseursRepository.findAll();
    }

    @Transactional
    public Fournisseurs save(Fournisseurs fournisseur) {
        if (fournisseur.getNom() == null || fournisseur.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom du fournisseur est obligatoire");
        }
        if (fournisseur.getTypeFournisseurs() == null || fournisseur.getTypeFournisseurs().getId() == null) {
            throw new IllegalArgumentException("Le type de fournisseur est obligatoire");
        }
        return fournisseursRepository.save(fournisseur);
    }

    @Transactional
    public void deleteById(Long id) {
        fournisseursRepository.deleteById(id);
    }
}