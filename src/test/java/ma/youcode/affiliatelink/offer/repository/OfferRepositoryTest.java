package ma.youcode.affiliatelink.offer.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ma.youcode.affiliatelink.offer.entity.Offer;
import ma.youcode.affiliatelink.offer.enums.Niche;

@DataJpaTest
@Testcontainers
class OfferRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18");

    @Autowired
    private OfferRepository offerRepository;

    @Test
    void findById_shouldReturnOffer_whenOfferExists() {

        // Arrange
        Offer offer = new Offer(1L, "Test Offer", "Test Description", "https://example.com", Niche.TECH);

        Offer savedOffer = offerRepository.save(offer);

        // Act
        Optional<Offer> result = offerRepository.findById(savedOffer.getId());

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(savedOffer.getId());
        assertThat(result.get().getTitle()).isEqualTo("Test Offer");
        assertThat(result.get().getNiche()).isEqualTo(Niche.TECH);
    }

    @Test
    void findById_shouldReturnEmpty_whenOfferDoesNotExist() {

        // Arrange
        Long offerId = 999L;

        // Act
        Optional<Offer> result = offerRepository.findById(offerId);

        // Assert
        assertThat(result).isEmpty();
    }
}
