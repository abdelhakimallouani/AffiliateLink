package ma.youcode.affiliatelink.offer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ma.youcode.affiliatelink.offer.dto.CampaignResponse;
import ma.youcode.affiliatelink.offer.enums.CampaignStatus;
import ma.youcode.affiliatelink.offer.service.CampaignService;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignQueryController {

    private final CampaignService campaignService;

    public CampaignQueryController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CampaignResponse> getCampaignByStatus(@RequestParam CampaignStatus status) {
        return campaignService.getCampaigns(status);
    }
}
