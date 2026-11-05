package ma.youcode.affiliatelink.offer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.youcode.affiliatelink.common.exception.InvalidStatusExcetion;
import ma.youcode.affiliatelink.common.exception.ResourceNotFoundException;
import ma.youcode.affiliatelink.offer.dto.CampaignResponse;
import ma.youcode.affiliatelink.offer.dto.CreateCampaignRequest;
import ma.youcode.affiliatelink.offer.dto.UpdateCampaignStatusRequest;
import ma.youcode.affiliatelink.offer.entity.Campaign;
import ma.youcode.affiliatelink.offer.entity.Offer;
import ma.youcode.affiliatelink.offer.enums.CampaignStatus;
import ma.youcode.affiliatelink.offer.repository.CampaignRepository;
import ma.youcode.affiliatelink.offer.repository.OfferRepository;

@Service
public class CampaignServiceImpl implements CampaignService {
    private final OfferRepository offerRepository;
    private final CampaignRepository campaignRepository;

    public CampaignServiceImpl(OfferRepository offerRepository, CampaignRepository campaignRepository) {
        this.offerRepository = offerRepository;
        this.campaignRepository = campaignRepository;
    }

    @Override
    @Transactional
    public CampaignResponse createCampaign(Long offerId, CreateCampaignRequest request) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("Offre introuvable avec l'id : " + offerId));

        if (!request.endDate().isAfter(request.startDate())) {
            throw new InvalidStatusExcetion("La date de fin doit etre apres la date de debut");
        }

        // LocalDate start = LocalDate.now();
        Campaign campaign = new Campaign(request.name().trim(), request.startDate(), request.endDate(),
                request.budget(), request.payoutModel(), request.payoutValue());

        offer.addCampaign(campaign);

        Campaign saveCampaign = campaignRepository.save(campaign);

        return new CampaignResponse(saveCampaign.getId(), offer.getId(), saveCampaign.getName(),
                saveCampaign.getStartDate(), saveCampaign.getEndDate(), saveCampaign.getBudget(),
                saveCampaign.getPayoutModel(), saveCampaign.getPayoutValue(), saveCampaign.getStatus());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignResponse> getCampaigns(CampaignStatus status) {
        List<Campaign> campaigns;
        if (status == null) {
            campaigns = campaignRepository.findAll();
        } else {
            campaigns = campaignRepository.findByStatus(status);
        }

        return campaigns.stream()
                .map(campaign -> new CampaignResponse(campaign.getId(), campaign.getOffer().getId(), campaign.getName(),
                        campaign.getStartDate(), campaign.getEndDate(), campaign.getBudget(), campaign.getPayoutModel(),
                        campaign.getPayoutValue(), campaign.getStatus()))
                .toList();
    }

    @Override
    @Transactional
    public void updateCampaignStatus(Long camId, UpdateCampaignStatusRequest request) {
        Campaign campaign = campaignRepository.findById(camId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + camId));

        CampaignStatus newStatus = request.status();

        if (campaign.getStatus() == newStatus) {
            throw new InvalidStatusExcetion("Campaign already has this status");
        }

        campaign.setStatus(newStatus);

        campaignRepository.save(campaign);
    }

}
