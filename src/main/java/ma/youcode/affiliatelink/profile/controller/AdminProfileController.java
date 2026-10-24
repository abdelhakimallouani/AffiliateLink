package ma.youcode.affiliatelink.profile.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ma.youcode.affiliatelink.profile.dto.UpdateProfileStatusRequest;
import ma.youcode.affiliatelink.profile.service.AdminProfileService;

@RestController
@RequestMapping("/api/admin/profiles")
public class AdminProfileController {
    private final AdminProfileService adminProfileService;

    public AdminProfileController(AdminProfileService adminProfileService) {
        this.adminProfileService = adminProfileService;
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void chnageStatus(@PathVariable Long id, @Valid @RequestBody UpdateProfileStatusRequest req) {
        adminProfileService.updateProfileStatus(id, req);
    }

}
