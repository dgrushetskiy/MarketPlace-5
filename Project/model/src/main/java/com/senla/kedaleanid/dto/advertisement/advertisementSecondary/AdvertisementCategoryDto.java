package com.senla.kedaleanid.dto.advertisement.advertisementSecondary;

import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;

import javax.validation.constraints.NotNull;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Convertible
public class AdvertisementCategoryDto implements IModelDto {
    @ReferencedField(name = "categoryId")
    @NotNull(groups = AdvertisementFilterDto.AdvFilterGroup.class)
    private Integer id;
    private String categoryName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
