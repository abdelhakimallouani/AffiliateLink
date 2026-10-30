package ma.youcode.affiliatelink.offer.service;

import ma.youcode.affiliatelink.common.exception.InvalidStatusExcetion;
import ma.youcode.affiliatelink.common.exception.ResourceNotFoundException;
import ma.youcode.affiliatelink.offer.dto.CreateOfferRequest;
import ma.youcode.affiliatelink.offer.dto.OfferResponse;
import ma.youcode.affiliatelink.offer.entity.Offer;
import ma.youcode.affiliatelink.offer.mapper.OfferMapper;
import ma.youcode.affiliatelink.offer.repository.OfferRepository;
import ma.youcode.affiliatelink.offer.repository.TagRepository;
import ma.youcode.affiliatelink.profile.api.AdvertiserAvailability;
import ma.youcode.affiliatelink.profile.api.ProfileApi;

public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final TagRepository tagRepository;
    private final OfferMapper offerMapper;
    private final ProfileApi api;

    public OfferServiceImpl(OfferRepository offerRepository, TagRepository tagRepository, OfferMapper offerMapper,
            ProfileApi api) {
        this.offerRepository = offerRepository;
        this.tagRepository = tagRepository;
        this.offerMapper = offerMapper;
        this.api = api;
    }

    @Override
    public OfferResponse craeteOffer(CreateOfferRequest req) {
        AdvertiserAvailability adv = api.getAdvertiserAvailability(req.advertiserId());

        if (!adv.exists()) {
            throw new ResourceNotFoundException(
                    "Annonceur introuvable : " + req.advertiserId());
        }
        if (!adv.active()) {
            throw new InvalidStatusExcetion(
                    "impossible de creer une offer : " + req.advertiserId());
        }

        Offer offer = offerMapper.toEntity(req);

        
    }

}
