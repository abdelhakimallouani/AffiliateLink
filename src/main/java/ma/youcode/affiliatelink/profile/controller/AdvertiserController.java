package ma.youcode.affiliatelink.profile.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ma.youcode.affiliatelink.profile.dto.AdvertiserResponse;
import ma.youcode.affiliatelink.profile.dto.CreateAdvertiserRequest;
import ma.youcode.affiliatelink.profile.service.AdvertiserService;

@RestController
@RequestMapping("/api/advertisers")
public class AdvertiserController {
    private final AdvertiserService advertiserService;

    public AdvertiserController(AdvertiserService advertiserService) {
        this.advertiserService = advertiserService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertiserResponse addAdvertiser(@Valid @RequestBody CreateAdvertiserRequest req) {
        AdvertiserResponse advResponse = advertiserService.createAdvertiser(req);
        return advResponse;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdvertiserResponse> showAdvertisers(){
        return advertiserService.showAdvertiser();
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdvertiserResponse findAdvertiser(@PathVariable Long id){
        return advertiserService.findAdvertiser(id);
    }

}
