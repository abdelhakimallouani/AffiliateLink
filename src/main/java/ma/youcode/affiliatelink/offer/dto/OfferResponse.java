package ma.youcode.affiliatelink.offer.dto;

import java.time.LocalDateTime;
import java.util.Set;

import ma.youcode.affiliatelink.offer.enums.Niche;

public record OfferResponse(
        Long id,
        Long advertiserId,
        String title,
        String description,
        String landingUrl,
        Niche niche,
        LocalDateTime createdAt,
        Set<TagResponse> tags) {
}
