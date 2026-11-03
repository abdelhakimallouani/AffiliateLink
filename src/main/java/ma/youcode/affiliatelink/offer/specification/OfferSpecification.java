// package ma.youcode.affiliatelink.offer.specification;

// import java.util.ArrayList;
// import java.util.List;
// import jakarta.persistence.criteria.Predicate;

// import org.springframework.data.jpa.domain.Specification;

// import ma.youcode.affiliatelink.offer.entity.Offer;
// import ma.youcode.affiliatelink.offer.enums.Niche;

// public class OfferSpecification {

//     public static Specification<Offer> filterOffers(Niche niche, Long advertiserId, String tag, String keyword) {

//     return (root , query, criteria) -> {
//         List<Predicate> predicates = new ArrayList<>();

//         if (niche != null) {
//             predicates.add(criteria.equal(root.get("niche"), niche));
//         }
//         if (advertiserId != null) {
//             predicates.add(criteria.equal(root.get("advertiserId"), advertiserId));
//         }
//         if (niche != null) {
//             predicates.add(criteria.equal(root.get("niche"), niche));
//         }
//     }
//     }
// }
