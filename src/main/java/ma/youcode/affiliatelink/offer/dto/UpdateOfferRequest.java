package ma.youcode.affiliatelink.offer.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import ma.youcode.affiliatelink.offer.enums.Niche;

public record UpdateOfferRequest(

        @NotBlank
        String title,

        @NotBlank
        String description,

        @NotBlank
        String targetUrl,

        @NotNull
        Niche niche,

        @NotEmpty
        Set<@NotBlank String> tags

) {
}

