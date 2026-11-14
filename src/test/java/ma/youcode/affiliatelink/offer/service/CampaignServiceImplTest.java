package ma.youcode.affiliatelink.offer.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import ma.youcode.affiliatelink.offer.dto.CampaignResponse;
import ma.youcode.affiliatelink.offer.dto.CreateCampaignRequest;
import ma.youcode.affiliatelink.offer.entity.Campaign;
import ma.youcode.affiliatelink.offer.entity.Offer;
import ma.youcode.affiliatelink.offer.enums.PayoutModel;
import ma.youcode.affiliatelink.offer.repository.CampaignRepository;
import ma.youcode.affiliatelink.offer.repository.OfferRepository;

@ExtendWith(MockitoExtension.class)
public class CampaignServiceImplTest {

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    @Test
    void createCampaign_shouldCreateCampaign_whenDataIsValid() {

        Long offerId = 1L;

        Offer offer = new Offer(offerId, null, null, null, null);
        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));

        CreateCampaignRequest request = new CreateCampaignRequest(
                "Campagne Test",
                LocalDateTime.of(2026, 12, 10, 10, 0),
                LocalDateTime.of(2026, 12, 20, 10, 0),
                new BigDecimal("1000"),
                PayoutModel.CPC,
                new BigDecimal("10"));

        when(campaignRepository.save(any(Campaign.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CampaignResponse response = campaignService.createCampaign(offerId, request);

        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo("Campagne Test");
        assertThat(response.startDate()).isEqualTo(request.startDate());
        assertThat(response.endDate()).isEqualTo(request.endDate());
        assertThat(response.budget()).isEqualByComparingTo("1000");
        assertThat(response.payoutModel()).isEqualTo(PayoutModel.CPC);
        assertThat(response.payoutValue()).isEqualByComparingTo("10");

        verify(offerRepository).findById(offerId);

        ArgumentCaptor<Campaign> captor = ArgumentCaptor.forClass(Campaign.class);

        verify(campaignRepository).save(captor.capture());

        Campaign savedCampaign = captor.getValue();

        assertThat(savedCampaign.getOffer()).isSameAs(offer);

    }

}
