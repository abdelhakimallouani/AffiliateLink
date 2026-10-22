package ma.youcode.affiliatelink.profile.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.youcode.affiliatelink.common.exception.EmailAlreadyUsedException;
import ma.youcode.affiliatelink.common.exception.ResourceNotFoundException;
import ma.youcode.affiliatelink.profile.dto.CreatePublisherRequest;
import ma.youcode.affiliatelink.profile.dto.PublisherResponse;
import ma.youcode.affiliatelink.profile.entity.Publisher;
import ma.youcode.affiliatelink.profile.repository.ProfileRepository;
import ma.youcode.affiliatelink.profile.repository.PublisherRepository;

@Service
public class PublisherServiceImpl implements PublisherService {
    private final ProfileRepository profileRepository;
    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(ProfileRepository profileRepository, PublisherRepository publisherRepository) {
        this.profileRepository = profileRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    @Transactional
    public PublisherResponse createPublisher(CreatePublisherRequest req) {
        String email = req.email().trim().toLowerCase();
        if (profileRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailAlreadyUsedException(email);
        }

        Publisher publisher = new Publisher(email, req.firstName().trim(), req.lastName().trim(), req.niche(),
                req.bio().trim(), req.country().trim());
        try {
            Publisher savePub = publisherRepository.save(publisher);
            return new PublisherResponse(savePub.getId(), savePub.getEmail(), savePub.getStatus(),
                    savePub.getFirstName(), savePub.getLastName(), savePub.getNiche(), savePub.getBio(),
                    savePub.getCountry());
        } catch (Exception e) {
            throw new EmailAlreadyUsedException(email);
        }
    }

    @Override
    public List<PublisherResponse> showPublishers() {
        List<PublisherResponse> publishers = publisherRepository
                .findAll().stream().map(pub -> new PublisherResponse(pub.getId(), pub.getEmail(), pub.getStatus(),
                        pub.getFirstName(), pub.getLastName(), pub.getNiche(), pub.getBio(), pub.getCountry()))
                .toList();
        return publishers;
    }

    @Override
    public PublisherResponse findPublisher(Long pubId) {

        if (pubId == null) {
            throw new ResourceNotFoundException("Publisher introuvable avec l'id : " + pubId);
        }

        Publisher pub = publisherRepository.findById(pubId).orElseThrow();
        return new PublisherResponse(pub.getId(), pub.getEmail(), pub.getStatus(), pub.getFirstName(),
                pub.getLastName(), pub.getNiche(), pub.getBio(), pub.getCountry());
    }

}
