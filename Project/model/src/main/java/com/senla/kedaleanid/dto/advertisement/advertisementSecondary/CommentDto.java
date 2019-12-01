package com.senla.kedaleanid.dto.advertisement.advertisementSecondary;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.advertisement.advertisement.AdvertisementInfoDto;
import com.senla.kedaleanid.dto.userdto.user.UserDto;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;
import com.senla.kedaleanid.utility.mapper.enumeration.PropertyType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Convertible
public class CommentDto implements IModelDto {
    @ReferencedField(name = "commentId")
    @Null(groups = LeaveCommentGroup.class)
    @NotNull(groups = {EditCommentGroup.class, DeleteCommentGroup.class})
    @Positive
    private Integer id;
    @ReferencedField(type = PropertyType.COMPOSITE)
    @NotNull(groups = {LeaveCommentGroup.class, EditCommentGroup.class, DeleteCommentGroup.class})
    private UserDto owner;
    @ReferencedField(type = PropertyType.COMPOSITE)
    @Null(groups = {EditCommentGroup.class, DeleteCommentGroup.class})
    @NotNull(groups = {LeaveCommentGroup.class})
    private AdvertisementInfoDto advertisement;
    @Size(min = 1, max = 450)
    @NotNull(groups = {LeaveCommentGroup.class})
    private String commentText;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Null(groups = {EditCommentGroup.class, DeleteCommentGroup.class, LeaveCommentGroup.class})
    private java.sql.Timestamp date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public AdvertisementInfoDto getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(AdvertisementInfoDto advertisement) {
        this.advertisement = advertisement;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public java.sql.Timestamp getDate() {
        return date;
    }

    public void setDate(java.sql.Timestamp date) {
        this.date = date;
    }

    public interface LeaveCommentGroup {
    }

    public interface EditCommentGroup {
    }

    public interface DeleteCommentGroup {
    }
}
