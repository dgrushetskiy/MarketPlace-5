package com.senla.kedaleanid.model.advertisement;

import com.senla.kedaleanid.model.IModel;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Entity(name = "AdvertisementPaidUntil")
@Table(name = "paid_ad", schema = "advertisements")
@Convertible
public class AdvertisementPaidUntil implements IModel {
    @Id
    @Column(name = "advertisement_id", updatable = false, nullable = false)
    @ReferencedField(name = "id")
    private Integer adId;
    @Column(name = "paid_until")
    private java.sql.Timestamp paidUntil;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    public AdvertisementPaidUntil() {
    }

    public AdvertisementPaidUntil(int adId, Timestamp paidUntil) {
        this.adId = adId;
        this.paidUntil = paidUntil;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public Timestamp getPaidUntil() {
        return paidUntil;
    }

    public void setPaidUntil(Timestamp paidUntil) {
        this.paidUntil = paidUntil;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdvertisementPaidUntil)) return false;
        AdvertisementPaidUntil that = (AdvertisementPaidUntil) o;
        return getAdId().equals(that.getAdId()) &&
                getPaidUntil().equals(that.getPaidUntil());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdId(), getPaidUntil());
    }

    @Override
    public String toString() {
        return "AdvertisementPaidUntilDto{" +
                "adId=" + adId +
                ", paidUntil=" + paidUntil +
                '}';
    }
}
