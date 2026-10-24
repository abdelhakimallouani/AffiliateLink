package ma.youcode.affiliatelink.profile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAdvertiserRequest(

        @NotBlank @Email @Size(max = 255) String email,

        @NotBlank @Size(max = 150) String companyName,

        @NotBlank @Size(max = 100) String sector,

        @Size(max = 255) String website

) {
}
