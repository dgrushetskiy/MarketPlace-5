package com.senla.kedaleanid.dto.transaction;

import com.senla.kedaleanid.utility.mapper.annotation.Convertible;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Created by earthofmarble on Oct, 2019
 */
@Convertible
public class PurposeDto {
    @NotNull(groups = AdvTransactionDto.PayAdGroup.class)
    @Positive
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
