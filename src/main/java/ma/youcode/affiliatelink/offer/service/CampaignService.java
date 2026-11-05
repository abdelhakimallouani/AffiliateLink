package ma.youcode.affiliatelink.offer.service;

import ma.youcode.affiliatelink.offer.dto.CampaignResponse;
import ma.youcode.affiliatelink.offer.dto.CreateCampaignRequest;

public interface CampaignService {

    CampaignResponse createCampaign(
            Long offerId,
            CreateCampaignRequest request);
}
