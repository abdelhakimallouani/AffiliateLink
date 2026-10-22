package ma.youcode.affiliatelink.profile.dto;

import ma.youcode.affiliatelink.profile.enums.Niche;
import ma.youcode.affiliatelink.profile.enums.ProfileStatus;

public record PublisherResponse(
        Long id,
        String email,
        ProfileStatus status,
        String firstName,
        String lastName,
        Niche niche,
        String bio,
        String country) {
}
