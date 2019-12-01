package com.senla.kedaleanid.model.advertisement;

import com.senla.kedaleanid.model.IModel;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Entity(name = "AdvertisementType")
@Table(name = "advertisement_type", schema = "advertisements")
@Convertible
public class AdvertisementType implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", updatable = false, nullable = false)
    @ReferencedField(name = "id")
    private Integer typeId;
    @Column(name = "type_name")
    private String typeName;
    @OneToMany(mappedBy = "adType", fetch = FetchType.LAZY)
    private List<Advertisement> advertisementList;

    public AdvertisementType() {
    }

    public AdvertisementType(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Advertisement> getAdvertisementList() {
        if (advertisementList == null) {
            advertisementList = new ArrayList<>();
        }
        return advertisementList;
    }

    public void setAdvertisementList(List<Advertisement> advertisementList) {
        this.advertisementList = advertisementList;
    }

    @Override
    public String toString() {
        return "AdvertisementType{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdvertisementType)) return false;
        AdvertisementType that = (AdvertisementType) o;
        return Objects.equals(getTypeId(), that.getTypeId()) &&
                getTypeName().equals(that.getTypeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeId(), getTypeName());
    }
}
