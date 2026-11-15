package ma.youcode.affiliatelink.profile.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import ma.youcode.affiliatelink.common.exception.ResourceNotFoundException;
import ma.youcode.affiliatelink.profile.dto.UpdateProfileStatusRequest;
import ma.youcode.affiliatelink.profile.service.AdminProfileService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AdminProfileController.class)
public class AdminProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminProfileService adminProfileService;

    @Test
    void updateProfileStatus_shouldReturn204_whenStatusIsUpdated() throws Exception {

        Long profileId = 1L;

        doNothing().when(adminProfileService).updateProfileStatus(eq(profileId), any(UpdateProfileStatusRequest.class));
        mockMvc.perform(patch("/api/admin/profiles/{id}/status", profileId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "status": "ACTIVE"
                        }
                        """))
                .andExpect(status().isNoContent());

        verify(adminProfileService).updateProfileStatus(eq(profileId), any(UpdateProfileStatusRequest.class));
    }

    @Test
    void updateProfileStatus_shouldReturn404_whenProfileNotFound()
            throws Exception {

        Long profileId = 99L;

        doThrow(new ResourceNotFoundException("Profile not found"))
                .when(adminProfileService)
                .updateProfileStatus(eq(profileId), any(UpdateProfileStatusRequest.class));

        mockMvc.perform(patch("/api/admin/profiles/{id}/status", profileId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "status": "ACTIVE"
                        }
                        """))
                .andExpect(status().isNotFound());
                // .andExpect(jsonPath("$.status").value(404))
                // .andExpect(jsonPath("$.detail").value("Profile not found"));
    }
}
