package ma.youcode.affiliatelink.offer.service;

import ma.youcode.affiliatelink.offer.dto.CreateOfferRequest;
import ma.youcode.affiliatelink.offer.dto.OfferResponse;

public interface OfferService {

    OfferResponse craeteOffer(CreateOfferRequest req);
}
