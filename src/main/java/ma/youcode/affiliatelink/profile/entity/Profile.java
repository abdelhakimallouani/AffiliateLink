package ma.youcode.affiliatelink.profile.entity;

import ma.youcode.affiliatelink.profile.enums.ProfileStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "profile_profiles")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfileStatus status = ProfileStatus.PENDING;

    protected Profile() {
    }

    protected Profile(String email) {
        this.email = email;
        this.status = ProfileStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public ProfileStatus getStatus() {
        return status;
    }

    public void setStatus(ProfileStatus status) {
        this.status = status;
    }

}
