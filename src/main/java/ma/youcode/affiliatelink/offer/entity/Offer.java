package ma.youcode.affiliatelink.offer.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String targetUrl;

    @Column(nullable = false)
    private Long advertiserId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Niche niche;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayoutModel payoutModel;

    @Column(nullable = false)
    private BigDecimal payoutValue;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "offer_offer_tags", joinColumns = @JoinColumn(name = "offer_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    protected Offer() {
    }

    public Offer(String title, String description, String targetUrl, Long advertiserId, Niche niche,
            PayoutModel payoutModel, BigDecimal payoutValue) {
        this.title = title;
        this.description = description;
        this.targetUrl = targetUrl;
        this.advertiserId = advertiserId;
        this.niche = niche;
        this.payoutModel = payoutModel;
        this.payoutValue = payoutValue;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
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

    public PayoutModel getPayoutModel() {
        return payoutModel;
    }

    public BigDecimal getPayoutValue() {
        return payoutValue;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Set<Tag> getTags() {
        return tags;
    }

}
