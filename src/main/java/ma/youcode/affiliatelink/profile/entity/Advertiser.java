package ma.youcode.affiliatelink.profile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile_advertisers")
@PrimaryKeyJoinColumn(name = "profile_id")
public class Advertiser extends Profile {

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String sector;

    @Column(length = 255)
    private String website;

    protected Advertiser() {
    }

    public Advertiser(String email, String companyName,
            String sector, String website) {
        super(email);
        this.companyName = companyName;
        this.sector = sector;
        this.website = website;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getSector() {
        return sector;
    }

    public String getWebsite() {
        return website;
    }
}
