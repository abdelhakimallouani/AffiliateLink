package ma.youcode.affiliatelink.profile.api;

// public interface ProfileApi {

//     boolean isActiveAdvertiser(Long advertiserId);
// }

public interface ProfileApi {

    AdvertiserAvailability getAdvertiserAvailability(Long advertiserId);
}