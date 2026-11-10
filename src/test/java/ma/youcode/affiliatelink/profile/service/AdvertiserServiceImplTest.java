package ma.youcode.affiliatelink.profile.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ma.youcode.affiliatelink.profile.dto.AdvertiserResponse;
import ma.youcode.affiliatelink.profile.dto.CreateAdvertiserRequest;
import ma.youcode.affiliatelink.profile.repository.AdvertiserRepository;
import ma.youcode.affiliatelink.profile.repository.ProfileRepository;

@ExtendWith(MockitoExtension.class)
public class AdvertiserServiceImplTest {
    @Mock
    private AdvertiserRepository advertiserRepository;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private AdvertiserServiceImpl advertiserService;

    @Test
    public void createAdvertiser_shouldCreateAdvertiser_whenEmailIsUnique() {

        CreateAdvertiserRequest request = new CreateAdvertiserRequest("allouani@gmail.com", "YouCode", "Dev",
                "www.youcode.ma");

        when(profileRepository.existsByEmailIgnoreCase("allouani@gmail.com")).thenReturn(false);
        when(advertiserRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        AdvertiserResponse response = advertiserService.createAdvertiser(request);

        assertNotNull(response);
        assertEquals("allouani@gmail.com", response.email());
        assertEquals("YouCode", response.companyName());
        assertEquals("Dev", response.sector());
        assertEquals("www.youcode.ma", response.website());

        verify(profileRepository).existsByEmailIgnoreCase("allouani@gmail.com");
        verify(advertiserRepository).save(any());
    }
}
