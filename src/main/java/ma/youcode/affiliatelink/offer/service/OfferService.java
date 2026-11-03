package ma.youcode.affiliatelink.offer.service;

import java.util.List;

import ma.youcode.affiliatelink.offer.dto.CreateOfferRequest;
import ma.youcode.affiliatelink.offer.dto.OfferResponse;

public interface OfferService {

    OfferResponse craeteOffer(CreateOfferRequest req);
    List<OfferResponse> getAllOffers();
}
