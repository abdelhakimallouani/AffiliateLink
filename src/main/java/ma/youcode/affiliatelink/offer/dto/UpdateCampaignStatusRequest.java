package ma.youcode.affiliatelink.offer.dto;

import jakarta.validation.constraints.NotNull;
import ma.youcode.affiliatelink.offer.enums.CampaignStatus;

public record UpdateCampaignStatusRequest(
        @NotNull CampaignStatus status) {
}
