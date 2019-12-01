package com.senla.kedaleanid.dto.advertisement.advertisement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementCategoryDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementPaidUntilDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementPhotoDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementTypeDto;
import com.senla.kedaleanid.dto.userdto.user.UserDto;
import com.senla.kedaleanid.model.advertisement.AdvertisementState;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;
import com.senla.kedaleanid.utility.mapper.enumeration.PropertyType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Convertible
public class AdvertisementExtendedDto implements IModelDto {
    @ReferencedField(name = "adId")
    @NotNull(groups = {EditAdGroup.class, DeleteAdGroup.class})
    @Null(groups = CreateAdGroup.class)
    @Positive
    private Integer id;
    @ReferencedField(type = PropertyType.COMPOSITE)
    @Valid
    @NotNull(groups = {EditAdGroup.class, DeleteAdGroup.class})
    private UserDto owner;
    @NotNull(groups = CreateAdGroup.class)
    private String adName;
    @NotNull(groups = CreateAdGroup.class)
    @PositiveOrZero
    private Double adPrice;
    @ReferencedField(name = "adPhotos", type = PropertyType.COLLECTION, thisContainsClass = AdvertisementPhotoDto.class)
    @Valid
    private List<AdvertisementPhotoDto> adPhotoUrl;
    @NotNull(groups = CreateAdGroup.class)
    @Size(min = 1, max = 499)
    private String adDescription;
    @ReferencedField(type = PropertyType.COMPOSITE)
    @Valid
    @NotNull(groups = CreateAdGroup.class)
    private AdvertisementCategoryDto adCategory;
    @ReferencedField(type = PropertyType.COMPOSITE)
    @Valid
    @NotNull(groups = CreateAdGroup.class)
    private AdvertisementTypeDto adType;
    @Valid
    @Null(groups = {EditAdGroup.class})
    private AdvertisementState adState;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Null(groups = {EditAdGroup.class})
    private Timestamp adDate;
    @ReferencedField(type = PropertyType.COMPOSITE)
    @Valid
    @Null(groups = EditAdGroup.class)
    private AdvertisementPaidUntilDto paidUntil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
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

    public AdvertisementState getAdState() {
        return adState;
    }

    public void setAdState(AdvertisementState adState) {
        this.adState = adState;
    }

    public Timestamp getAdDate() {
        return adDate;
    }

    public void setAdDate(Timestamp adDate) {
        this.adDate = adDate;
    }

    public AdvertisementPaidUntilDto getPaidUntil() {
        return paidUntil;
    }

    public void setPaidUntil(AdvertisementPaidUntilDto paidUntil) {
        this.paidUntil = paidUntil;
    }

    public interface CreateAdGroup {
    }

    public interface EditAdGroup {
    }

    public interface DeleteAdGroup {
    }

}
