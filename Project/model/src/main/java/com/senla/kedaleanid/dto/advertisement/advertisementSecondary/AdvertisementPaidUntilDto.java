package com.senla.kedaleanid.dto.advertisement.advertisementSecondary;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;

import java.sql.Timestamp;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Convertible
public class AdvertisementPaidUntilDto implements IModelDto {
    @ReferencedField(name = "adId")
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private java.sql.Timestamp paidUntil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getPaidUntil() {
        return paidUntil;
    }

    public void setPaidUntil(Timestamp paidUntil) {
        this.paidUntil = paidUntil;
    }
}
