package ma.youcode.affiliatelink.profile.service;

import ma.youcode.affiliatelink.profile.dto.AdvertiserResponse;
import ma.youcode.affiliatelink.profile.dto.CreateAdvertiserRequest;

public interface AdvertiserService {

    AdvertiserResponse createAdvertiser(CreateAdvertiserRequest request);
}
