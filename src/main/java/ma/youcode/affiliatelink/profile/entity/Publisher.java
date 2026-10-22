package ma.youcode.affiliatelink.profile.entity;

import ma.youcode.affiliatelink.profile.enums.Niche;
import jakarta.persistence.*;

@Entity
@Table(name = "profile_publishers")
@PrimaryKeyJoinColumn(name = "profile_id")
public class Publisher extends Profile {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Niche niche;

    @Column(length = 1000)
    private String bio;

    @Column(nullable = false)
    private String country;

    protected Publisher() {
    }

    public Publisher(String email, String firstName, String lastName, Niche niche, String bio, String country) {

        super(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.niche = niche;
        this.bio = bio;
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Niche getNiche() {
        return niche;
    }

    public String getBio() {
        return bio;
    }

    public String getCountry() {
        return country;
    }
}
