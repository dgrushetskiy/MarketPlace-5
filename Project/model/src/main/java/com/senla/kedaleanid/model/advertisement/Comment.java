package com.senla.kedaleanid.model.advertisement;

import com.senla.kedaleanid.model.IModel;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;
import com.senla.kedaleanid.utility.mapper.enumeration.PropertyType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Entity(name = "Comment")
@Table(name = "advertisement_comment", schema = "advertisements")
@NamedEntityGraph(name = "commentGraph",
        attributeNodes = {@NamedAttributeNode(value = "owner", subgraph = "ownerGraph"),
                @NamedAttributeNode(value = "advertisement")},
        subgraphs = @NamedSubgraph(name = "ownerGraph",
                attributeNodes = @NamedAttributeNode(value = "photoUrl")))
@Convertible
public class Comment implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", updatable = false, nullable = false)
    @ReferencedField(name = "id")
    private Integer commentId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_owner_id")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private User owner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_ad_id")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private Advertisement advertisement;
    @Column(name = "comment_message")
    private String commentText;
    @Column(name = "comment_date")
    private java.sql.Timestamp date;

    public Comment() {
    }

    public Comment(User owner, String commentText, java.sql.Timestamp date, Advertisement advertisement) {
        this.owner = owner;
        this.commentText = commentText;
        this.date = date;
        this.advertisement = advertisement;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", owner=" + owner +
                ", errormodel=" + advertisement +
                ", commentText='" + commentText + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(getCommentId(), comment.getCommentId()) &&
                getOwner().equals(comment.getOwner()) &&
                getAdvertisement().equals(comment.getAdvertisement()) &&
                getCommentText().equals(comment.getCommentText()) &&
                getDate().equals(comment.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommentId(), getOwner(), getAdvertisement(), getCommentText(), getDate());
    }
}
