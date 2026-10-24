package ma.youcode.affiliatelink.profile.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.youcode.affiliatelink.common.exception.EmailAlreadyUsedException;
import ma.youcode.affiliatelink.profile.dto.AdvertiserResponse;
import ma.youcode.affiliatelink.profile.dto.CreateAdvertiserRequest;
import ma.youcode.affiliatelink.profile.entity.Advertiser;
import ma.youcode.affiliatelink.profile.repository.AdvertiserRepository;
import ma.youcode.affiliatelink.profile.repository.ProfileRepository;

@Service
public class AdvertiserServiceImpl implements AdvertiserService {
    private final ProfileRepository profileRepository;
    private final AdvertiserRepository advertiserRepository;

    public AdvertiserServiceImpl(ProfileRepository profileRepository, AdvertiserRepository advertiserRepository) {
        this.profileRepository = profileRepository;
        this.advertiserRepository = advertiserRepository;
    }

    @Override
    @Transactional
    public AdvertiserResponse createAdvertiser(CreateAdvertiserRequest req) {
        String email = req.email().trim().toLowerCase();

        if (profileRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailAlreadyUsedException(email);
        }

        Advertiser advertiser = new Advertiser(email, req.companyName(), req.sector().trim(), req.website().trim());

        try {
            Advertiser adv = advertiserRepository.save(advertiser);

            AdvertiserResponse advRespense = new AdvertiserResponse(adv.getId(), adv.getEmail(), adv.getStatus(),
                    adv.getCompanyName(), adv.getSector(), adv.getWebsite());
            return advRespense;
        } catch (Exception e) {
            throw new EmailAlreadyUsedException(email);
        }
    }

}
