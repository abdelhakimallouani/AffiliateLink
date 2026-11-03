package ma.youcode.affiliatelink.offer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ma.youcode.affiliatelink.offer.entity.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> , JpaSpecificationExecutor<Offer> {
}
