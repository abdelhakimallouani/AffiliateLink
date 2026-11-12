package ma.youcode.affiliatelink.offer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;
import java.util.Set;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ma.youcode.affiliatelink.common.exception.ResourceNotFoundException;
import ma.youcode.affiliatelink.offer.dto.CreateOfferRequest;
import ma.youcode.affiliatelink.offer.dto.OfferResponse;
import ma.youcode.affiliatelink.offer.entity.Offer;
import ma.youcode.affiliatelink.offer.entity.Tag;
import ma.youcode.affiliatelink.offer.enums.Niche;
import ma.youcode.affiliatelink.offer.mapper.OfferMapper;
import ma.youcode.affiliatelink.offer.repository.OfferRepository;
import ma.youcode.affiliatelink.offer.repository.TagRepository;
import ma.youcode.affiliatelink.profile.api.AdvertiserAvailability;
import ma.youcode.affiliatelink.profile.api.ProfileApi;

@ExtendWith(MockitoExtension.class)
public class OfferServiceImplTest {

    @Mock
    private OfferRepository offerRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private OfferMapper offerMapper;
    @Mock
    private ProfileApi api;
    @InjectMocks
    private OfferServiceImpl offerService;

    @Test
    public void createOffer_shouldCreateOffer_whenAdvertiserIsActive() {

        Long advId = 1L;

        CreateOfferRequest request = new CreateOfferRequest(advId, "Offer1", "Offer Description", "http://example.com",
                Niche.TECH, Set.of("tag1", "tag2"));

        AdvertiserAvailability adv = new AdvertiserAvailability(true, true);

        Offer offer = mock(Offer.class);
        OfferResponse response = mock(OfferResponse.class);

        when(api.getAdvertiserAvailability(advId)).thenReturn(adv);
        when(tagRepository.findByName(any())).thenReturn(Optional.empty());
        when(tagRepository.save(any(Tag.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(offerMapper.toEntity(request)).thenReturn(offer);
        when(offerRepository.save(offer)).thenReturn(offer);
        when(offerMapper.toResponse(offer)).thenReturn(response);

        OfferResponse result = offerService.craeteOffer(request);

        assertEquals(response, result);

        verify(api).getAdvertiserAvailability(advId);
        verify(tagRepository, times(2)).findByName(any());
        verify(tagRepository, times(2)).save(any(Tag.class));
        verify(offerMapper).toEntity(request);
        verify(offerRepository).save(offer);
        verify(offerMapper).toResponse(offer);
    }

    @Test
    void createOffer_shouldThrowException_whenAdvertiserNotFound() {
        Long advId = 1L;

        CreateOfferRequest request = new CreateOfferRequest(advId, "Offer1", "Offer Description", "http://example.com",
                Niche.TECH, Set.of("tag1", "tag2"));

        AdvertiserAvailability adv = new AdvertiserAvailability(false, false);

        when(api.getAdvertiserAvailability(advId)).thenReturn(adv);

        assertThrows(ResourceNotFoundException.class, () -> offerService.craeteOffer(request));

        verify(api).getAdvertiserAvailability(advId);
        verifyNoInteractions(tagRepository, offerRepository, offerMapper);

    }

}
