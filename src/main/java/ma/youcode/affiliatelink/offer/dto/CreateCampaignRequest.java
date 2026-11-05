package ma.youcode.affiliatelink.offer.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import ma.youcode.affiliatelink.offer.enums.PayoutModel;

public record CreateCampaignRequest(

        @NotBlank @Size(max = 150) String name,

        @NotNull LocalDateTime startDate,

        @NotNull LocalDateTime endDate,

        @NotNull BigDecimal budget,

        @NotNull PayoutModel payoutModel,

        @NotNull BigDecimal payoutValue

) {
}
