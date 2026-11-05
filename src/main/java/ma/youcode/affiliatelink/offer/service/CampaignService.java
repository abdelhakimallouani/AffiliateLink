package ma.youcode.affiliatelink.offer.service;

import java.util.List;

import ma.youcode.affiliatelink.offer.dto.CampaignResponse;
import ma.youcode.affiliatelink.offer.dto.CreateCampaignRequest;
import ma.youcode.affiliatelink.offer.dto.UpdateCampaignStatusRequest;
import ma.youcode.affiliatelink.offer.enums.CampaignStatus;

public interface CampaignService {

    CampaignResponse createCampaign(
            Long offerId,
            CreateCampaignRequest request);

    List<CampaignResponse> getCampaigns(CampaignStatus status);

    void updateCampaignStatus(Long camId, UpdateCampaignStatusRequest request);
}
