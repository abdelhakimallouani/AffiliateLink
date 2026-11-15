package ma.youcode.affiliatelink.offer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ma.youcode.affiliatelink.offer.entity.Campaign;
import ma.youcode.affiliatelink.offer.entity.Offer;
import ma.youcode.affiliatelink.offer.enums.CampaignStatus;
import ma.youcode.affiliatelink.offer.enums.Niche;
import ma.youcode.affiliatelink.offer.enums.PayoutModel;

@DataJpaTest
@Testcontainers
class CampaignRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18");

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private OfferRepository offerRepository;

    private Offer createOffer(Long advertiserId, String title) {
        Offer offer = new Offer(advertiserId, title, "Test Description", "https://example.com", Niche.TECH);

        return offerRepository.save(offer);
    }

    private Campaign createCampaign(Offer offer, String name, CampaignStatus status) {
        Campaign campaign = new Campaign(name, LocalDateTime.now(), LocalDateTime.now().plusDays(10),
                new BigDecimal("1000"), PayoutModel.CPC, new BigDecimal("10"));
        campaign.setOffer(offer);
        campaign.setStatus(status);
        return campaignRepository.save(campaign);
    }

    @Test
    void findById_shouldReturnCampaign_whenCampaignExists() {

        // Arrange
        Offer offer = createOffer(1L, "Test Offer");

        Campaign savedCampaign = createCampaign(offer, "Test Campaign", CampaignStatus.ACTIVE);

        // Act
        Optional<Campaign> result = campaignRepository.findById(savedCampaign.getId());

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(savedCampaign.getId());
        assertThat(result.get().getName()).isEqualTo("Test Campaign");
    }
}
