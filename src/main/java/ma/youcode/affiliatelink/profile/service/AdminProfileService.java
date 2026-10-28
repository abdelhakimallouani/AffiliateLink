package ma.youcode.affiliatelink.profile.service;

import ma.youcode.affiliatelink.profile.dto.UpdateProfileStatusRequest;

public interface AdminProfileService {
    void updateProfileStatus(Long id, UpdateProfileStatusRequest request);
}
