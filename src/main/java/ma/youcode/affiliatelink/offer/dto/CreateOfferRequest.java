package ma.youcode.affiliatelink.offer.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

import ma.youcode.affiliatelink.offer.enums.Niche;

public record CreateOfferRequest(

        @NotNull @Positive Long advertiserId,

        @NotBlank String title,

        @NotBlank String description,

        @NotBlank String landingUrl,

        @NotNull Niche niche,

        @NotNull List<@NotBlank String> tags

) {
}
