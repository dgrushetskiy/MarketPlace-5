package com.senla.kedaleanid.dto.advertisement.advertisementSecondary;

import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.advertisement.advertisement.AdvertisementInfoDto;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;
import com.senla.kedaleanid.utility.mapper.enumeration.PropertyType;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Convertible
public class AdvertisementPhotoDto implements IModelDto {

    private Integer id;
    private Integer adId;
    private String photoUrl;
    private boolean isMain;
    @ReferencedField(type = PropertyType.COMPOSITE)
    private AdvertisementInfoDto advertisement;

    public AdvertisementInfoDto getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(AdvertisementInfoDto advertisement) {
        this.advertisement = advertisement;
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

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }
}
