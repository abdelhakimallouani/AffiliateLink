package ma.youcode.affiliatelink.profile.repository;

import ma.youcode.affiliatelink.profile.entity.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertiserRepository
        extends JpaRepository<Advertiser, Long> {
}
