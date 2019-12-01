package com.senla.kedaleanid.model.advertisement;

import com.senla.kedaleanid.model.IModel;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.utility.defaultgraph.DefaultGraph;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;
import com.senla.kedaleanid.utility.mapper.enumeration.PropertyType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by earthofmarble on Sep, 2019
 */

@Entity(name = "Advertisement")
@Table(name = "advertisement", schema = "advertisements")
@NamedEntityGraphs({@NamedEntityGraph(name = "advertisementExtendedGraph",
        attributeNodes = {@NamedAttributeNode(value = "owner", subgraph = "ownerSubGraph"),
                @NamedAttributeNode(value = "adPhotos"),
                @NamedAttributeNode(value = "adCategory"),
                @NamedAttributeNode(value = "adType"),
                @NamedAttributeNode(value = "paidUntil")},
        subgraphs = @NamedSubgraph(name = "ownerSubGraph",
                attributeNodes = @NamedAttributeNode(value = "photoUrl"))),
        @NamedEntityGraph(name = "advertisementGraph",
                attributeNodes = {@NamedAttributeNode(value = "adPhotos"),
                        @NamedAttributeNode(value = "adCategory"),
                        @NamedAttributeNode(value = "adType"),
                        @NamedAttributeNode(value = "paidUntil")})})
@Convertible
@DefaultGraph(name = "advertisementExtendedGraph")
public class Advertisement implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advertisement_id", updatable = false, nullable = false)
    @ReferencedField(name = "id")
    private Integer adId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_owner_id")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private User owner;
    @Column(name = "advertisement_name")
    private String adName;
    @Column(name = "advertisement_price")
    private Double adPrice;
    @OneToMany(mappedBy = "advertisement", cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @ReferencedField(name = "adPhotoUrl", type = PropertyType.COLLECTION, thisContainsClass = AdvertisementPhoto.class)
    private List<AdvertisementPhoto> adPhotos;
    @Column(name = "advertisement_description")
    private String adDescription;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_category")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private AdvertisementCategory adCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_type")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private AdvertisementType adType;
    @Column(name = "advertisement_state")
    @Enumerated(EnumType.STRING)
    private AdvertisementState adState;
    @Column(name = "advertisement_date")
    private java.sql.Timestamp adDate;
    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("date DESC")
    private List<Comment> adComments;
    @OneToOne(mappedBy = "advertisement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ReferencedField(type = PropertyType.COMPOSITE)
    private AdvertisementPaidUntil paidUntil;

    public Advertisement() {
    }

    public Advertisement(User owner, String adName, double adPrice, List<AdvertisementPhoto> adPhotos,
                         String adDescription, AdvertisementCategory adCategory, AdvertisementType adType,
                         AdvertisementState adState, java.sql.Timestamp adDate) {
        this.owner = owner;
        this.adName = adName;
        this.adPrice = adPrice;
        this.adPhotos = adPhotos;
        this.adDescription = adDescription;
        this.adCategory = adCategory;
        this.adType = adType;
        this.adState = adState;
        this.adDate = adDate;
    }

    public List<Comment> getAdComments() {
        if (adComments == null) {
            adComments = new ArrayList<>();
        }
        return adComments;
    }

    public void setAdComments(List<Comment> adComments) {
        this.adComments = adComments;
    }

    public AdvertisementPaidUntil getPaidUntil() {
        return paidUntil;
    }

    public void setPaidUntil(AdvertisementPaidUntil paidUntil) {
        this.paidUntil = paidUntil;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public Double getAdPrice() {
        return adPrice;
    }

    public void setAdPrice(double adPrice) {
        this.adPrice = adPrice;
    }

    public List<AdvertisementPhoto> getAdPhotos() {
        return adPhotos;
    }

    public void setAdPhotos(List<AdvertisementPhoto> adPhotos) {
        this.adPhotos = adPhotos;
    }

    public String getAdDescription() {
        return adDescription;
    }

    public void setAdDescription(String adDescription) {
        this.adDescription = adDescription;
    }

    public AdvertisementCategory getAdCategory() {
        return adCategory;
    }

    public void setAdCategory(AdvertisementCategory adCategory) {
        this.adCategory = adCategory;
    }

    public AdvertisementType getAdType() {
        return adType;
    }

    public void setAdType(AdvertisementType adType) {
        this.adType = adType;
    }

    public AdvertisementState getAdState() {
        return adState;
    }

    public void setAdState(AdvertisementState adState) {
        this.adState = adState;
    }

    public java.sql.Timestamp getAdDate() {
        return adDate;
    }

    public void setAdDate(java.sql.Timestamp adDate) {
        this.adDate = adDate;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "adId=" + adId +
                ", owner=" + owner +
                ", adName='" + adName + '\'' +
                ", adPrice=" + adPrice +
                ", adDescription='" + adDescription + '\'' +
                ", adCategory='" + adCategory + '\'' +
                ", adType='" + adType + '\'' +
                ", adState='" + adState + '\'' +
                ", adDate='" + adDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Advertisement)) return false;
        Advertisement that = (Advertisement) o;
        return Objects.equals(getAdId(), that.getAdId()) &&
                Double.compare(that.getAdPrice(), getAdPrice()) == 0 &&
                getOwner().equals(that.getOwner()) &&
                getAdName().equals(that.getAdName()) &&
                Objects.equals(getAdPhotos(), that.getAdPhotos()) &&
                getAdDescription().equals(that.getAdDescription()) &&
                getAdCategory().equals(that.getAdCategory()) &&
                getAdType().equals(that.getAdType()) &&
                getAdState() == that.getAdState() &&
                getAdDate().equals(that.getAdDate()) &&
                getPaidUntil().equals(that.getPaidUntil());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdId(), getOwner(), getAdName(), getAdPrice(), getAdPhotos(),
                getAdDescription(), getAdCategory(), getAdType(), getAdState(), getAdDate());
    }
}
