package ma.youcode.affiliatelink.profile.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.youcode.affiliatelink.common.exception.InvalidStatusExcetion;
import ma.youcode.affiliatelink.common.exception.ResourceNotFoundException;
import ma.youcode.affiliatelink.profile.dto.UpdateProfileStatusRequest;
import ma.youcode.affiliatelink.profile.entity.Profile;
import ma.youcode.affiliatelink.profile.enums.ProfileStatus;
import ma.youcode.affiliatelink.profile.event.ProfileSuspended;
import ma.youcode.affiliatelink.profile.repository.ProfileRepository;

@Service
public class AdminProfileServiceImpl implements AdminProfileService {
    private final ProfileRepository profileRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AdminProfileServiceImpl(ProfileRepository profileRepository, ApplicationEventPublisher eventPublisher) {
        this.profileRepository = profileRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public void updateProfileStatus(Long id, UpdateProfileStatusRequest req) {

        if (id == null) {
            throw new ResourceNotFoundException("Profil introuvable avec l'id : " + id);
        }

        Profile profile = profileRepository.findById(id).orElseThrow();
        ProfileStatus status = profile.getStatus();
        ProfileStatus newStatus = req.status();

        boolean changeAuthorise1 = status == ProfileStatus.PENDING && newStatus == ProfileStatus.ACTIVE;
        boolean changeAuthorise2 = status == ProfileStatus.ACTIVE && newStatus == ProfileStatus.SUSPENDED;
        boolean changeAuthorise3 = status == ProfileStatus.SUSPENDED && newStatus == ProfileStatus.ACTIVE;

        if (!(changeAuthorise1 || changeAuthorise2 || changeAuthorise3)) {
            throw new InvalidStatusExcetion("Changement de statut non autorise : " + status + " -> " + newStatus);
        }

        profile.setStatus(newStatus);
        if (newStatus == ProfileStatus.SUSPENDED) {
            eventPublisher.publishEvent(new ProfileSuspended(profile.getId()));
        }

    }

}
