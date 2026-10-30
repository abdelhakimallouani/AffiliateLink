package ma.youcode.affiliatelink.offer.mapper;

import org.mapstruct.Mapper;

import ma.youcode.affiliatelink.offer.dto.TagResponse;
import ma.youcode.affiliatelink.offer.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponse toResponse(Tag tag);
}
