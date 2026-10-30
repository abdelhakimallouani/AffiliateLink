package ma.youcode.affiliatelink.offer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ma.youcode.affiliatelink.offer.dto.CreateOfferRequest;
import ma.youcode.affiliatelink.offer.dto.OfferResponse;
import ma.youcode.affiliatelink.offer.entity.Offer;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface OfferMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "campaigns", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Offer toEntity(CreateOfferRequest req);

    OfferResponse toResponse(Offer offer);
}
