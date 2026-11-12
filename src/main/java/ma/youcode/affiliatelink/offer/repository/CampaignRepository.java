package ma.youcode.affiliatelink.offer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ma.youcode.affiliatelink.offer.entity.Campaign;
import ma.youcode.affiliatelink.offer.enums.CampaignStatus;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByStatus(CampaignStatus status);

    // @Query("""
    //         SELECT c , c.offer.title FROM Campaign c WHERE c.offer.advertiserId = :advertiserId, c.status = :status ORDER BY c.startDate
    //         """)
    // public List<Campaign> allCampaigns(@Param("advertiserId") Long advertiserId, @Param ("status") CampaignStatus status);
}
