package ma.youcode.affiliatelink.profile.service;

import java.util.List;

import ma.youcode.affiliatelink.profile.dto.AdvertiserResponse;
import ma.youcode.affiliatelink.profile.dto.CreateAdvertiserRequest;

public interface AdvertiserService {

    AdvertiserResponse createAdvertiser(CreateAdvertiserRequest request);
    List<AdvertiserResponse> showAdvertiser();
    AdvertiserResponse findAdvertiser(Long advId);
}
