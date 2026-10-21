package ma.youcode.affiliatelink.profile.dto;

import ma.youcode.affiliatelink.profile.enums.Niche;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePublisherRequest(

        @NotBlank @Email String email,

        @NotBlank String firstName,

        @NotBlank String lastName,

        @NotNull Niche niche,

        String bio,

        @NotBlank String country

) {
}
