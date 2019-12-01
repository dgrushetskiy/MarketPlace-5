package com.senla.kedaleanid.dto.userdto.userSecondary;

import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.userdto.user.UserProfileDto;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Convertible
public class UserPhotoDto implements IModelDto {
    @ReferencedField(name = "userId")
    @Null(groups = {UserProfileDto.CreateUserGroup.class})
    private Integer id; //owner Id
    @ReferencedField(name = "photoUrl")
    @NotNull(groups = {UserProfileDto.CreateUserGroup.class})
    private String photoUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
