package ma.youcode.affiliatelink.profile.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import ma.youcode.affiliatelink.common.exception.InvalidStatusExcetion;
import ma.youcode.affiliatelink.profile.dto.UpdateProfileStatusRequest;
import ma.youcode.affiliatelink.profile.entity.Profile;
import ma.youcode.affiliatelink.profile.enums.ProfileStatus;
import ma.youcode.affiliatelink.profile.repository.ProfileRepository;

@ExtendWith(MockitoExtension.class)
public class AdminProfileServiceImplTest {
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private AdminProfileServiceImpl adminProfileService;

    @Test
    public void updateProfileStatus_shouldActivatePendingProfile() {
        // arrange
        Long profildId = 1L;

        Profile profile = mock(Profile.class);
        when(profileRepository.findById(profildId)).thenReturn(Optional.of(profile));
        when(profile.getStatus()).thenReturn(ProfileStatus.PENDING);

        UpdateProfileStatusRequest request = new UpdateProfileStatusRequest(ProfileStatus.ACTIVE);

        // act
        adminProfileService.updateProfileStatus(profildId, request);

        // assert
        verify(profile).setStatus(ProfileStatus.ACTIVE);
        verify(profileRepository).findById(profildId);
        verify(eventPublisher, never()).publishEvent(any());

    }

    @Test
    public void updateProfileStatus_shouldSuspendActiveProfile() {
        // arrang
        Long profildId = 2L;

        Profile profile = mock(Profile.class);
        when(profileRepository.findById(profildId)).thenReturn(Optional.of(profile));
        when(profile.getStatus()).thenReturn(ProfileStatus.ACTIVE);

        UpdateProfileStatusRequest request = new UpdateProfileStatusRequest(ProfileStatus.SUSPENDED);

        // act
        adminProfileService.updateProfileStatus(profildId, request);

        // assert
        verify(profile).setStatus(ProfileStatus.SUSPENDED);
        verify(profileRepository).findById(profildId);
        verify(eventPublisher, never()).publishEvent(any());

    }

    @Test
    void updateProfileStatus_shouldThrowException_whenProfileNotFound() {
        Long profilId = 999L;

        when(profileRepository.findById(profilId)).thenReturn(Optional.empty());

        UpdateProfileStatusRequest request = new UpdateProfileStatusRequest(ProfileStatus.ACTIVE);
        // assertThrows(ResourceNotFoundException.class, () -> adminProfileService.updateProfileStatus(profilId, request));
        assertThrows(NoSuchElementException.class, () -> adminProfileService.updateProfileStatus(profilId, request));
        verify(profileRepository).findById(profilId);
        verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    void updateProfileStatus_shouldThrowException_whenTransitionIsInvalid(){
        
        Long profilId = 3L;

        Profile profile = mock(Profile.class);
        when(profileRepository.findById(profilId)).thenReturn(Optional.of(profile));
        when(profile.getStatus()).thenReturn(ProfileStatus.PENDING);

        UpdateProfileStatusRequest request = new UpdateProfileStatusRequest(ProfileStatus.SUSPENDED);

        assertThrows(InvalidStatusExcetion.class, () -> adminProfileService.updateProfileStatus(profilId, request));
        verify(profileRepository).findById(profilId);
        verify(eventPublisher, never()).publishEvent(any());
    }
}
