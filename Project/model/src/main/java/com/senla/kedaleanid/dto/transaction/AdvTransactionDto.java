package com.senla.kedaleanid.dto.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.advertisement.advertisement.AdvertisementInfoDto;
import com.senla.kedaleanid.dto.userdto.user.UserInfoDto;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;
import com.senla.kedaleanid.utility.mapper.enumeration.PropertyType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Convertible
public class AdvTransactionDto implements IModelDto {
    @Null(groups = PayAdGroup.class)
    @Positive
    private Integer id;
    @ReferencedField(type = PropertyType.COMPOSITE)
    @Valid
    @NotNull(groups = PayAdGroup.class)
    private UserInfoDto owner;
    @Valid
    @NotNull(groups = PayAdGroup.class)
    private AdvertisementInfoDto advertisement;
    @NotNull
    @PositiveOrZero(groups = PayAdGroup.class)
    private Double amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Null(groups = PayAdGroup.class)
    private Timestamp date;
    @NotNull
    @Positive(groups = PayAdGroup.class)
    private Integer hours;
    @ReferencedField(type = PropertyType.COMPOSITE)
    @Valid
    @NotNull(groups = PayAdGroup.class)
    private PurposeDto purpose;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserInfoDto getOwner() {
        return owner;
    }

    public void setOwner(UserInfoDto owner) {
        this.owner = owner;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public AdvertisementInfoDto getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(AdvertisementInfoDto advertisement) {
        this.advertisement = advertisement;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public PurposeDto getPurpose() {
        return purpose;
    }

    public void setPurpose(PurposeDto purpose) {
        this.purpose = purpose;
    }

    public interface PayAdGroup {
    }
}
