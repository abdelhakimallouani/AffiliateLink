package ma.youcode.affiliatelink.offer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import ma.youcode.affiliatelink.offer.dto.CreateOfferRequest;
import ma.youcode.affiliatelink.offer.dto.OfferResponse;
import ma.youcode.affiliatelink.offer.enums.Niche;
import ma.youcode.affiliatelink.offer.service.OfferService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping("/api/offres")
public class OfferController {
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OfferResponse createOffer(@Valid @RequestBody CreateOfferRequest request) {

        OfferResponse response = offerService.craeteOffer(request);
        return response;
    }

    // @GetMapping
    // @ResponseStatus(HttpStatus.OK)
    // public List<OfferResponse> getAllOffers() {
    //     return offerService.getAllOffers();
    // }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OfferResponse> getOfferPages(@RequestParam(required = false) Niche niche,
            @RequestParam(required = false) Long advertiserId, @RequestParam(required = false) String tag,
            @RequestParam(required = false) String keyword,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        if (advertiserId != null && advertiserId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "advertiserId doit etre positif");
        }

        Page<OfferResponse> offers = offerService.getOffers(niche, advertiserId, tag, keyword, pageable);
        return offers;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OfferResponse getOfferById()

}
