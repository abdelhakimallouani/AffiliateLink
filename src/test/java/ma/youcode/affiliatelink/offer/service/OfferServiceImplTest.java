package ma.youcode.affiliatelink.offer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import static org.assertj.core.api.Assertions.*;

import ma.youcode.affiliatelink.common.exception.InvalidStatusExcetion;
import ma.youcode.affiliatelink.common.exception.ResourceNotFoundException;
import ma.youcode.affiliatelink.offer.dto.CreateOfferRequest;
import ma.youcode.affiliatelink.offer.dto.OfferResponse;
import ma.youcode.affiliatelink.offer.dto.UpdateOfferRequest;
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

    // creation offer
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

    @Test
    void createOffer_shouldThrowException_whenAdvertiserIsInactive() {
        Long advId = 1L;

        CreateOfferRequest request = new CreateOfferRequest(advId, "Offer1", "Offer Description", "http://example.com",
                Niche.TECH, Set.of("tag1", "tag2"));

        AdvertiserAvailability adv = new AdvertiserAvailability(true, false);

        when(api.getAdvertiserAvailability(advId)).thenReturn(adv);

        assertThrows(InvalidStatusExcetion.class, () -> offerService.craeteOffer(request));

        verify(api).getAdvertiserAvailability(advId);
        verifyNoInteractions(tagRepository, offerRepository, offerMapper);

    }

    // test offer by id
    @Test
    void getOfferById_shouldReturnOffer_whenOfferExists() {
        Long advId = 1L;

        Offer offer = mock(Offer.class);
        OfferResponse response = mock(OfferResponse.class);

        when(offerRepository.findById(advId)).thenReturn(Optional.of(offer));
        when(offerMapper.toResponse(offer)).thenReturn(response);

        OfferResponse offerResponse = offerService.getOfferById(advId);

        assertEquals(response, offerResponse);

        verify(offerRepository).findById(advId);
        verify(offerMapper).toResponse(offer);

    }

    @Test
    void getOfferById_shouldThrowException_whenOfferNotFound() {
        Long offerId = 999L;

        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> offerService.getOfferById(offerId));

        verify(offerRepository).findById(offerId);
        verifyNoInteractions(offerMapper);
    }

    // get offer by search or filter
    @Test
    void getOffers_shouldReturnOffers_whenNoFiltersProvided() {
        Pageable pageable = PageRequest.of(0, 10);

        Offer offer = mock(Offer.class);
        OfferResponse respense = mock(OfferResponse.class);

        mockSearchResult(pageable, offer, respense);

        Page<OfferResponse> result = offerService.getOffers(null, null, null, null, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).containsExactly(respense);

        verify(offerRepository).findAll(
                ArgumentMatchers.<Specification<Offer>>any(),
                eq(pageable));

        verify(offerMapper).toResponse(offer);

    }

    @Test
    void getOffers_shouldReturnOffersFilteredByAdvertiserId() {

        // Arrange
        Long advertiserId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        Offer offer = mock(Offer.class);
        OfferResponse response = mock(OfferResponse.class);

        mockSearchResult(pageable, offer, response);

        // Act
        Page<OfferResponse> result = offerService.getOffers(
                null,
                advertiserId,
                null,
                null,
                pageable);

        // Assert
        assertThat(result.getContent()).containsExactly(response);

        // Verify
        verify(offerRepository).findAll(
                ArgumentMatchers.<Specification<Offer>>any(),
                eq(pageable));

        verify(offerMapper).toResponse(offer);
    }

    @Test
    void getOffers_shouldReturnOffers_whenMultipleFiltersProvided() {

        // Arrange
        Niche niche = Niche.TECH;
        Long advertiserId = 1L;
        String tag = "java";
        String keyword = "Spring";

        Pageable pageable = PageRequest.of(0, 10);

        Offer offer = mock(Offer.class);
        OfferResponse response = mock(OfferResponse.class);

        mockSearchResult(pageable, offer, response);

        // Act
        Page<OfferResponse> result = offerService.getOffers(
                niche,
                advertiserId,
                tag,
                keyword,
                pageable);

        // Assert
        assertThat(result.getContent()).containsExactly(response);

        // Verify
        verify(offerRepository).findAll(
                ArgumentMatchers.<Specification<Offer>>any(),
                eq(pageable));

        verify(offerMapper).toResponse(offer);
    }

    @Test
    void updateOffer_shouldUpdateOffer_whenOfferExists() {
        Long offerId = 1L;
        UpdateOfferRequest request = new UpdateOfferRequest("offfer2", "offer Desprcrition",
                "https://example-offer.com", Niche.FOOD, Set.of());
        Offer offer = mock(Offer.class);
        OfferResponse response = mock(OfferResponse.class);

        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));
        when(offer.getTags()).thenReturn(new LinkedHashSet<>());
        when(offerRepository.save(offer)).thenReturn(offer);
        when(offerMapper.toResponse(offer)).thenReturn(response);

        OfferResponse offerResponse = offerService.updateOffer(offerId, request);

        assertEquals(response, offerResponse);

        verify(offerRepository).findById(offerId);
        verify(offer).setTitle(request.title());
        verify(offer).setDescription(request.description());
        verify(offer).setTargetUrl(request.targetUrl());
        verify(offer).setNiche(request.niche());

        verify(offerRepository).save(offer);
        verify(offerMapper).toResponse(offer);

    }

    private void mockSearchResult(
            Pageable pageable,
            Offer offer,
            OfferResponse response) {
        when(offerRepository.findAll(
                ArgumentMatchers.<Specification<Offer>>any(),
                eq(pageable))).thenReturn(new PageImpl<>(List.of(offer), pageable, 1));

        when(offerMapper.toResponse(offer)).thenReturn(response);
    }

}
