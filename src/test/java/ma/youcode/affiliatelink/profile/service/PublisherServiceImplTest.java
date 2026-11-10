package ma.youcode.affiliatelink.profile.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ma.youcode.affiliatelink.profile.dto.CreatePublisherRequest;
import ma.youcode.affiliatelink.profile.dto.PublisherResponse;
import ma.youcode.affiliatelink.profile.enums.Niche;
import ma.youcode.affiliatelink.profile.enums.ProfileStatus;
import ma.youcode.affiliatelink.profile.repository.ProfileRepository;
import ma.youcode.affiliatelink.profile.repository.PublisherRepository;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceImplTest {

    @Mock
    private PublisherRepository publisherRepository;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    @Test
    public void createPublisher_shouldCreatePublisher_whenEmailIsUnique() {

        // arrange
        CreatePublisherRequest request = new CreatePublisherRequest("hakim@gmail.com", "abdelhakim", "allouani",
                Niche.TECH, "java", "maroc");

        when(profileRepository.existsByEmailIgnoreCase("hakim@gmail.com")).thenReturn(false);

        when(publisherRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PublisherResponse response = publisherService.createPublisher(request);

        assertNotNull(response);
        assertEquals("hakim@gmail.com", response.email());
        assertEquals(ProfileStatus.PENDING, response.status());
        assertEquals("abdelhakim", response.firstName());
        assertEquals("allouani", response.lastName());
        assertEquals(Niche.TECH, response.niche());
        assertEquals("java", response.bio());
        assertEquals("maroc", response.country());

        verify(profileRepository).existsByEmailIgnoreCase("hakim@gmail.com");
        verify(publisherRepository).save(any());
    }
}
