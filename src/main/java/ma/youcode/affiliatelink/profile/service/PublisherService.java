package ma.youcode.affiliatelink.profile.service;

import ma.youcode.affiliatelink.profile.dto.CreatePublisherRequest;
import ma.youcode.affiliatelink.profile.dto.PublisherResponse;

public interface PublisherService {

    PublisherResponse createPublisher(CreatePublisherRequest request);
}

