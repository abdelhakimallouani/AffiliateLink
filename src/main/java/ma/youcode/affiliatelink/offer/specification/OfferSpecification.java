package ma.youcode.affiliatelink.offer.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import ma.youcode.affiliatelink.offer.entity.Offer;
import ma.youcode.affiliatelink.offer.entity.Tag;
import ma.youcode.affiliatelink.offer.enums.Niche;

public class OfferSpecification {

    public static Specification<Offer> filterOffers(Niche niche, Long advertiserId, String tag, String keyword) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (niche != null) {
                predicates.add(criteriaBuilder.equal(root.get("niche"), niche));
            }
            if (advertiserId != null) {
                predicates.add(criteriaBuilder.equal(root.get("advertiserId"), advertiserId));
            }
            if (tag != null && !tag.isBlank()) {

                Join<Offer, Tag> tagJoin = root.join("tags");
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(tagJoin.get("name")), tag.trim().toLowerCase()));

                query.distinct(true);
            }
            if (keyword != null && !keyword.isBlank()) {

                String search = "%" + keyword.trim().toLowerCase() + "%";

                Predicate titlePre = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), search);
                Predicate descriptionPre = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), search);

                predicates.add(criteriaBuilder.or(titlePre, descriptionPre));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
