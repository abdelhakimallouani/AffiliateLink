package ma.youcode.affiliatelink.profile.dto;

import ma.youcode.affiliatelink.profile.enums.ProfileStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateProfileStatusRequest(
        @NotNull ProfileStatus status) {
}
