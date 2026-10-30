package ma.youcode.affiliatelink.offer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.youcode.affiliatelink.offer.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);
}
