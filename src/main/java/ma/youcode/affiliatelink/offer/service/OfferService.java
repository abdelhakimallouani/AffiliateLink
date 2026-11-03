package ma.youcode.affiliatelink.offer.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ma.youcode.affiliatelink.offer.dto.CreateOfferRequest;
import ma.youcode.affiliatelink.offer.dto.OfferResponse;
import ma.youcode.affiliatelink.offer.enums.Niche;

public interface OfferService {

    OfferResponse craeteOffer(CreateOfferRequest req);
    List<OfferResponse> getAllOffers();
    Page<OfferResponse> getOffers(Niche niche, Long advertiserId, String tag, String keyword, Pageable pageable);
}
