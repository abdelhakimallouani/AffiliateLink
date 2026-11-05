package ma.youcode.affiliatelink.offer.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import ma.youcode.affiliatelink.offer.enums.CampaignStatus;
import ma.youcode.affiliatelink.offer.enums.PayoutModel;

public record CampaignResponse(
        Long id,
        Long offerId,
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate,
        BigDecimal budget,
        PayoutModel payoutModel,
        BigDecimal payoutValue,
        CampaignStatus status) {
}
