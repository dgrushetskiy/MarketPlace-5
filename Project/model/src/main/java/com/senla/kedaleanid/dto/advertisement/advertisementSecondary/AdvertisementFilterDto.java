package com.senla.kedaleanid.dto.advertisement.advertisementSecondary;

import com.senla.kedaleanid.dto.IModelDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by earthofmarble on Oct, 2019
 */

public class AdvertisementFilterDto implements IModelDto {
    @Null(groups = AdvFilterGroup.class)
    private Integer id;
    @Size(min = 1, max = 15, groups = AdvFilterGroup.class)
    private String name;
    @PositiveOrZero(groups = AdvFilterGroup.class)
    private Long minPrice;
    @Positive(groups = AdvFilterGroup.class)
    private Long maxPrice;
    @Valid
    @NotEmpty(groups = AdvFilterGroup.class)
    private List<AdvertisementCategoryDto> categoryList;
    @Valid
    @NotEmpty(groups = AdvFilterGroup.class)
    private List<AdvertisementTypeDto> typeList;

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

    public Long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Long minPrice) {
        this.minPrice = minPrice;
    }

    public Long getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<AdvertisementCategoryDto> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<AdvertisementCategoryDto> categoryList) {
        this.categoryList = categoryList;
    }

    public List<AdvertisementTypeDto> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<AdvertisementTypeDto> typeList) {
        this.typeList = typeList;
    }

    public interface AdvFilterGroup {
    }
}
