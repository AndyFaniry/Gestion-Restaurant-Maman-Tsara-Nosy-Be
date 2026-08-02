package com.gestion.restaurant.specification.livraisons;

import com.gestion.restaurant.dto.livraisons.ZoneLivraisonFilterDto;
import com.gestion.restaurant.entity.livraisons.ZonesLivraison;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ZoneLivraisonSpecification {

    public static Specification<ZonesLivraison> getSpecifications(ZoneLivraisonFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter != null) {
                if (StringUtils.hasText(filter.getSearch())) {
                    String searchTerm = "%" + filter.getSearch().trim().toLowerCase() + "%";
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("libelle")), searchTerm));
                }

                if (filter.getMaxPrix() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("prix"), filter.getMaxPrix()));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}