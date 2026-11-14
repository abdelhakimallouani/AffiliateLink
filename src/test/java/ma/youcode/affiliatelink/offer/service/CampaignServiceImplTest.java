package ma.youcode.affiliatelink.offer.service;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
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

import ma.youcode.affiliatelink.common.exception.InvalidStatusExcetion;
import ma.youcode.affiliatelink.offer.dto.CampaignResponse;
import ma.youcode.affiliatelink.offer.dto.CreateCampaignRequest;
import ma.youcode.affiliatelink.offer.dto.UpdateCampaignStatusRequest;
import ma.youcode.affiliatelink.offer.entity.Campaign;
import ma.youcode.affiliatelink.offer.entity.Offer;
import ma.youcode.affiliatelink.offer.enums.CampaignStatus;
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

    @Test
    void createCampaign_shouldThrowException_whenEndDateIsBeforeStartDate() {

        Long offerId = 1L;

        Offer offer = new Offer(offerId, null, null, null, null);
        when(offerRepository.findById(offerId))
                .thenReturn(Optional.of(offer));

        CreateCampaignRequest request = new CreateCampaignRequest(
                "Campagne Test",
                LocalDateTime.of(2026, 12, 10, 10, 0),
                LocalDateTime.of(2026, 12, 5, 10, 0),
                new BigDecimal("1000"),
                PayoutModel.CPC,
                new BigDecimal("10"));

        assertThrows(InvalidStatusExcetion.class, () -> campaignService.createCampaign(offerId, request));

        verify(campaignRepository, never()).save(any(Campaign.class));
    }

    @Test
    void updateCampaignStatus_shouldAllowValidTransitions() {
        Long campaignId = 1L;
        Campaign campaign = new Campaign("Test Campaign", LocalDateTime.now(), LocalDateTime.now().plusDays(10), new BigDecimal("1000"), PayoutModel.CPC, new BigDecimal("10"));
        campaign.setStatus(CampaignStatus.DRAFT);
        
        when(campaignRepository.findById(campaignId)).thenReturn(Optional.of(campaign));

        UpdateCampaignStatusRequest request = new UpdateCampaignStatusRequest(CampaignStatus.ACTIVE);

        campaignService.updateCampaignStatus(campaignId, request);

        assertThat(campaign.getStatus()).isEqualTo(CampaignStatus.ACTIVE);
        verify(campaignRepository).save(campaign);
    }

}
