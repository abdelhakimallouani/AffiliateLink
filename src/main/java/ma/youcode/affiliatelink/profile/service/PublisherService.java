package ma.youcode.affiliatelink.profile.service;

import java.util.List;

import ma.youcode.affiliatelink.profile.dto.CreatePublisherRequest;
import ma.youcode.affiliatelink.profile.dto.PublisherResponse;

public interface PublisherService {

    PublisherResponse createPublisher(CreatePublisherRequest request);
    List<PublisherResponse> showPublishers();
    PublisherResponse findPublisher(Long pubId);
}

