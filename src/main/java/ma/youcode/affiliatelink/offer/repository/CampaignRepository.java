package ma.youcode.affiliatelink.offer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.youcode.affiliatelink.offer.entity.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    
}
