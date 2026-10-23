package ma.youcode.affiliatelink.profile.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ma.youcode.affiliatelink.profile.dto.CreatePublisherRequest;
import ma.youcode.affiliatelink.profile.dto.PublisherResponse;
import ma.youcode.affiliatelink.profile.service.PublisherService;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    public ResponseEntity<PublisherResponse> createPub(@Valid @RequestBody CreatePublisherRequest req) {
        PublisherResponse publisher = publisherService.createPublisher(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(publisher);
    }
    
    @GetMapping
    public ResponseEntity<List<PublisherResponse>> showPublishers(){
        return ResponseEntity.ok(publisherService.showPublishers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherResponse> showPubl(@PathVariable Long id){
        return ResponseEntity.ok(publisherService.findPublisher(id));
    }

}
