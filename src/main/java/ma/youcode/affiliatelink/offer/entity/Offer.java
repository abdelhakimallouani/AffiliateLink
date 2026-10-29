package ma.youcode.affiliatelink.offer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import ma.youcode.affiliatelink.offer.enums.Niche;
import ma.youcode.affiliatelink.offer.enums.PayoutModel;

@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long advertiserId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String targetUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Niche niche;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private List<Campaign> campaigns = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "offer_offer_tags", joinColumns = @JoinColumn(name = "offer_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    protected Offer() {
    }

    public Offer(Long advertiserId, String title, String description, String targetUrl, Niche niche) {
        this.advertiserId = advertiserId;
        this.title = title;
        this.description = description;
        this.targetUrl = targetUrl;
        this.niche = niche;

    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
    }

    public void addCampaign(Campaign campaign) {
        campaigns.add(campaign);
        campaign.setOffer(this);
    }

    public void removeCampaign(Campaign campaign) {
        campaigns.remove(campaign);
        campaign.setOffer(null);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getOffers().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getOffers().remove(this);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public Long getAdvertiserId() {
        return advertiserId;
    }

    public Niche getNiche() {
        return niche;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public Set<Tag> getTags() {
        return tags;
    }

}
