package ma.youcode.affiliatelink.profile.dto;

import ma.youcode.affiliatelink.profile.enums.ProfileStatus;

public record AdvertiserResponse(
        Long id,
        String email,
        ProfileStatus status,
        String companyName,
        String sector,
        String website) {
}
