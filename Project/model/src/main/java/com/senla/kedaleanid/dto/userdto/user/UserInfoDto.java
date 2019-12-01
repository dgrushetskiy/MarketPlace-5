package com.senla.kedaleanid.dto.userdto.user;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.transaction.AdvTransactionDto;
import com.senla.kedaleanid.model.user.UserRole;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Created by earthofmarble on Sep, 2019
 */
@JacksonXmlRootElement
@Convertible
public class UserInfoDto implements IModelDto {
    @NotNull(groups = {DeleteUserGroup.class, AdvTransactionDto.PayAdGroup.class})
    @Positive
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private UserRole role;
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

    public interface DeleteUserGroup {
    }

}
