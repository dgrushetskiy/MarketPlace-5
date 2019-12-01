package com.senla.kedaleanid.model.user;

import com.senla.kedaleanid.model.IModel;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Entity(name = "UserPhoto")
@Table(name = "user_photo", schema = "advertisements")
@Convertible
public class UserPhoto implements IModel {
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    @ReferencedField(name = "id")
    private Integer userId;
    @Column(name = "photo_url")
    @ReferencedField(name = "photoUrl")
    private String photoUrl;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    public UserPhoto() {
    }

    public UserPhoto(Integer userId, String photoUrl) {
        this.userId = userId;
        this.photoUrl = photoUrl;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPhoto)) return false;
        UserPhoto userPhoto = (UserPhoto) o;
        return getUserId().equals(userPhoto.getUserId()) &&
                getPhotoUrl().equals(userPhoto.getPhotoUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getPhotoUrl());
    }

    @Override
    public String toString() {
        return "UserPhoto{" +
                "userId=" + userId +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
