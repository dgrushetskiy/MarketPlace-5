package com.senla.kedaleanid.dto.userdto.userSecondary;

import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Convertible
public class SecretCodeDto implements IModelDto {
    @Null
    private Integer id;
    @NotNull
    @Positive
    private Integer userId;
    @NotNull
    @Positive
    @Min(1)
    @Max(50000)
    private Integer code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
