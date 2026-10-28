// package ma.youcode.affiliatelink.offer.entity;

// import java.math.BigDecimal;
// import java.time.LocalDateTime;
// import java.util.HashSet;
// import java.util.Set;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.JoinTable;
// import jakarta.persistence.ManyToMany;
// import jakarta.persistence.Table;
// import ma.youcode.affiliatelink.offer.enums.Niche;
// import ma.youcode.affiliatelink.offer.enums.PayoutModel;

// @Entity
// @Table(name = "offers")
// public class Offer {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(nullable = false)
//     private String title;

//     @Column(nullable = false)
//     private String description;

//     @Column(nullable = false)
//     private String targetUrl;

//     @Column(nullable = false)
//     private Long advertiserId;

//     @Enumerated(EnumType.STRING)
//     @Column(nullable = false)
//     private Niche niche;

//     @Enumerated(EnumType.STRING)
//     @Column(nullable = false)
//     private PayoutModel payoutModel;

//     @Column(nullable = false)
//     private BigDecimal payoutValue;

//     @Column(nullable = false)
//     private boolean active = true;

//     @Column(nullable = false, updatable = false)
//     private LocalDateTime createdAt;

//         @Column(nullable = false)
//     private LocalDateTime updatedAt;

//     @ManyToMany(fetch = FetchType.LAZY)
//     @JoinTable(
//         name = "offer_offer_tags",
//         joinColumns = @JoinColumn(name = "offer_id"),
//         inverseJoinColumns = @JoinColumn(name = "tag_id")
//     )
//     private Set<Tag> tags = new HashSet<>();
// }
