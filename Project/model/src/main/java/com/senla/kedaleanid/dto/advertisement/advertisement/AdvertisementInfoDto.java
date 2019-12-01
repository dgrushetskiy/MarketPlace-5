package com.senla.kedaleanid.dto.advertisement.advertisement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.transaction.AdvTransactionDto;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Convertible
public class AdvertisementInfoDto implements IModelDto {
    @ReferencedField(name = "adId")
    @NotNull(groups = {AdvTransactionDto.PayAdGroup.class})
    private Integer id;
    private String adName;
    private Double adPrice;
    private String adDescription;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp adDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAdDescription() {
        return adDescription;
    }

    public void setAdDescription(String adDescription) {
        this.adDescription = adDescription;
    }

    public Timestamp getAdDate() {
        return adDate;
    }

    public void setAdDate(Timestamp adDate) {
        this.adDate = adDate;
    }
}
