package com.senla.kedaleanid.model.advertisement;

import com.senla.kedaleanid.model.IModel;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;
import com.senla.kedaleanid.utility.mapper.enumeration.PropertyType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Entity(name = "AdvertisementPhoto")
@Table(name = "advertisement_photo", schema = "advertisements")
@Convertible
public class AdvertisementPhoto implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "advertisement_id")
    private Integer adId;
    @Column(name = "photo_url")
    private String photoUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id", insertable = false, updatable = false)
    @ReferencedField(type = PropertyType.COMPOSITE)
    private Advertisement advertisement;
    @Column(name = "is_main")
    private boolean isMain;

    public AdvertisementPhoto() {
    }

    public AdvertisementPhoto(Integer adId, String photoUrl, boolean isMain) {
        this.adId = adId;
        this.photoUrl = photoUrl;
        this.isMain = isMain;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdvertisementPhoto)) return false;
        AdvertisementPhoto that = (AdvertisementPhoto) o;
        return isMain() == that.isMain() &&
                getAdId().equals(that.getAdId()) &&
                getPhotoUrl().equals(that.getPhotoUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdId(), getPhotoUrl(), isMain());
    }

    @Override
    public String toString() {
        return "AdvertisementPhoto{" +
                "adId=" + adId +
                ", photoUrl='" + photoUrl + '\'' +
                ", isMain=" + isMain +
                '}';
    }
}
