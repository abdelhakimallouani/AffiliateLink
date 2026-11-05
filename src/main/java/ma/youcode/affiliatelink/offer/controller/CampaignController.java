package ma.youcode.affiliatelink.offer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ma.youcode.affiliatelink.offer.dto.CampaignResponse;
import ma.youcode.affiliatelink.offer.dto.CreateCampaignRequest;
import ma.youcode.affiliatelink.offer.service.CampaignService;

@RestController
@RequestMapping("/api/offres/{id}/campaigns")
public class CampaignController {
    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CampaignResponse createCampaign(@PathVariable("id") Long offerId,
            @Valid @RequestBody CreateCampaignRequest request) {
        return campaignService.createCampaign(offerId, request);
    }

}
