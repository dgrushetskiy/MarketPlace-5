package com.senla.kedaleanid.dto.userdto.userSecondary;

import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.userdto.user.UserProfileDto;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Convertible
public class UserCredsDto implements IModelDto {
    @ReferencedField(name = "userId")
    @Null(groups = {UserProfileDto.CreateUserGroup.class})
    private Integer id; //owner id
    @Null(groups = UserProfileDto.UpdateUserGroup.class)
    @NotNull(groups = {UserProfileDto.CreateUserGroup.class, SignInGroup.class})
    private String login;
    @NotNull(groups = {UserProfileDto.CreateUserGroup.class, SignInGroup.class, UserProfileDto.UpdateUserGroup.class})
    @Size(min = 3, max = 50, groups = {UserProfileDto.CreateUserGroup.class, UserProfileDto.UpdateUserGroup.class, SignInGroup.class})
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public interface SignInGroup {
    }
}
