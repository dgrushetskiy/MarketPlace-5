package com.senla.kedaleanid.dto.advertisement.advertisement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementCategoryDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementPaidUntilDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementPhotoDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementTypeDto;
import com.senla.kedaleanid.model.advertisement.AdvertisementState;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;
import com.senla.kedaleanid.utility.mapper.enumeration.PropertyType;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Convertible
public class AdvertisementDto implements IModelDto {
    @ReferencedField(name = "adId")
    private Integer id;
    private String adName;
    private Double adPrice;
    @ReferencedField(name = "adPhotos", type = PropertyType.COLLECTION, thisContainsClass = AdvertisementPhotoDto.class)
    private List<AdvertisementPhotoDto> adPhotoUrl;
    private String adDescription;
    @ReferencedField(type = PropertyType.COMPOSITE)
    private AdvertisementCategoryDto adCategory;
    @ReferencedField(type = PropertyType.COMPOSITE)
    private AdvertisementTypeDto adType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp adDate;
    @ReferencedField(type = PropertyType.COMPOSITE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private AdvertisementPaidUntilDto paidUntil;
    private AdvertisementState adState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AdvertisementPaidUntilDto getPaidUntil() {
        return paidUntil;
    }

    public void setPaidUntil(AdvertisementPaidUntilDto paidUntil) {
        this.paidUntil = paidUntil;
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

    public void setAdPrice(Double adPrice) {
        this.adPrice = adPrice;
    }

    public List<AdvertisementPhotoDto> getAdPhotoUrl() {
        return adPhotoUrl;
    }

    public void setAdPhotoUrl(List<AdvertisementPhotoDto> adPhotoUrl) {
        this.adPhotoUrl = adPhotoUrl;
    }

    public String getAdDescription() {
        return adDescription;
    }

    public void setAdDescription(String adDescription) {
        this.adDescription = adDescription;
    }

    public AdvertisementCategoryDto getAdCategory() {
        return adCategory;
    }

    public void setAdCategory(AdvertisementCategoryDto adCategory) {
        this.adCategory = adCategory;
    }

    public AdvertisementTypeDto getAdType() {
        return adType;
    }

    public void setAdType(AdvertisementTypeDto adType) {
        this.adType = adType;
    }

    public Timestamp getAdDate() {
        return adDate;
    }

    public void setAdDate(Timestamp adDate) {
        this.adDate = adDate;
    }

    public AdvertisementState getAdState() {
        return adState;
    }

    public void setAdState(AdvertisementState adState) {
        this.adState = adState;
    }
}
