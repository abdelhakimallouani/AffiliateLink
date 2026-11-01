package ma.youcode.affiliatelink.profile.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.youcode.affiliatelink.profile.api.AdvertiserAvailability;
import ma.youcode.affiliatelink.profile.api.ProfileApi;
import ma.youcode.affiliatelink.profile.enums.ProfileStatus;
import ma.youcode.affiliatelink.profile.repository.AdvertiserRepository;

@Service
@Transactional(readOnly = true)
public class ProfileApiImpl implements ProfileApi {
    private final AdvertiserRepository advertiserRepository;

    public ProfileApiImpl(AdvertiserRepository advertiserRepository) {
        this.advertiserRepository = advertiserRepository;
    }

    // @Override
    // public boolean isActiveAdvertiser(Long advId) {
    //     return advertiserRepository.findById(advId).map(a -> a.getStatus() == ProfileStatus.ACTIVE).orElse(false);
    // }

    @Override
    public AdvertiserAvailability getAdvertiserAvailability(Long advertiserId) {

        return advertiserRepository.findById(advertiserId)
                .map(advertiser -> new AdvertiserAvailability(
                        true,
                        advertiser.getStatus() == ProfileStatus.ACTIVE))
                .orElse(new AdvertiserAvailability(false, false));
    }

}
