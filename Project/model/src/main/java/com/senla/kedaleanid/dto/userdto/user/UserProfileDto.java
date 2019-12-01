package com.senla.kedaleanid.dto.userdto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.userdto.userSecondary.UserCredsDto;
import com.senla.kedaleanid.dto.userdto.userSecondary.UserPhotoDto;
import com.senla.kedaleanid.model.user.UserRole;
import com.senla.kedaleanid.utility.customvalidation.email.ExtendedEmail;
import com.senla.kedaleanid.utility.customvalidation.phonenumber.PhoneNumber;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;
import com.senla.kedaleanid.utility.mapper.enumeration.PropertyType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;

/**
 * Created by earthofmarble on Sep, 2019
 */
@JacksonXmlRootElement
@Convertible
public class UserProfileDto implements IModelDto {
    @NotNull(message = "id is required", groups = UpdateUserGroup.class)
    @Null(groups = CreateUserGroup.class)
    private Integer id;
    @ReferencedField(type = PropertyType.COMPOSITE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Valid
    @NotNull(groups = CreateUserGroup.class)
    private UserCredsDto userCreds;
    @ReferencedField(name = "firstName")
    @NotNull(groups = CreateUserGroup.class)
    private String firstName;
    @ReferencedField(name = "lastName")
    @NotNull(groups = CreateUserGroup.class)
    private String lastName;
    @ReferencedField(name = "phoneNumber")
    @NotNull(groups = CreateUserGroup.class)
    @PhoneNumber(groups = {CreateUserGroup.class, UpdateUserGroup.class})
    private String phoneNumber;
    @NotNull(groups = CreateUserGroup.class)
    @ExtendedEmail(groups = {CreateUserGroup.class, UpdateUserGroup.class})
    private String email;
    @ReferencedField(name = "photoUrl", type = PropertyType.COMPOSITE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Valid
    private UserPhotoDto photoUrl;
    @ReferencedField(name = "role")
    @Null(groups = {CreateUserGroup.class, UpdateUserGroup.class})
    private UserRole role;
    @ReferencedField(name = "rating")
    @NotNull(groups = CreateUserGroup.class)
    private Integer rating;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserCredsDto getUserCreds() {
        return userCreds;
    }

    public void setUserCreds(UserCredsDto userCreds) {
        this.userCreds = userCreds;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserPhotoDto getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(UserPhotoDto photoUrl) {
        this.photoUrl = photoUrl;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public interface CreateUserGroup {
    }

    public interface UpdateUserGroup {
    }

}
