package com.senla.kedaleanid.model.user;

import com.senla.kedaleanid.model.IModel;
import com.senla.kedaleanid.model.advertisement.Advertisement;
import com.senla.kedaleanid.model.advertisement.Advertisement_;
import com.senla.kedaleanid.model.advertisement.Comment;
import com.senla.kedaleanid.model.chat.Chat;
import com.senla.kedaleanid.model.chat.Message;
import com.senla.kedaleanid.model.transaction.Transaction;
import com.senla.kedaleanid.utility.defaultgraph.DefaultGraph;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;
import com.senla.kedaleanid.utility.mapper.enumeration.PropertyType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by earthofmarble on Sep, 2019
 */

@Entity(name = "User")
@Table(name = "user", schema = "advertisements")
@NamedEntityGraphs(value = {@NamedEntityGraph(name = "userInfoGraph"),
        @NamedEntityGraph(name = "userGraph",
                attributeNodes = @NamedAttributeNode(value = "photoUrl")),
        @NamedEntityGraph(name = "userProfileGraph",
                attributeNodes = {@NamedAttributeNode(value = "userCreds"),
                        @NamedAttributeNode(value = "photoUrl")}),
        @NamedEntityGraph(name = "userExtendedGraph",
                attributeNodes = {@NamedAttributeNode(value = "photoUrl"),
                        @NamedAttributeNode(value = User_.ADVERTISEMENTS, subgraph = "adGraph")},
                subgraphs = @NamedSubgraph(name = "adGraph",
                        attributeNodes = {@NamedAttributeNode(value = Advertisement_.AD_PHOTOS),
                                @NamedAttributeNode(value = Advertisement_.AD_CATEGORY),
                                @NamedAttributeNode(value = Advertisement_.AD_TYPE)}))
})
@Convertible
@DefaultGraph(name = "userExtendedGraph", fetchType = "fetchgraph")
public class User implements IModel {
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private UserCreds userCreds;
    @Column(name = "user_first_name")
    @ReferencedField(name = "firstName")
    private String firstName;
    @Column(name = "user_last_name")
    @ReferencedField(name = "lastName")
    private String lastName;
    @ReferencedField(name = "phoneNumber")
    @Column(name = "user_phone_number")
    private String phoneNumber;
    @Column(name = "user_email")
    private String email;
    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ReferencedField(name = "photoUrl", type = PropertyType.COMPOSITE)
    private UserPhoto photoUrl;
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    @ReferencedField(name = "role")
    private UserRole role;
    @Column(name = "user_rating")
    @ReferencedField(name = "rating")
    private Integer rating;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SecretCode> secretCode;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Transaction> transactions;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Comment> comments;
    @ManyToMany(mappedBy = "userList", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Chat> chats;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ReferencedField(name = "advertisements", type = PropertyType.COLLECTION,
            thisCollectionType = SortedSet.class, thisContainsClass = Advertisement.class)
    private Set<Advertisement> advertisements;
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Message> messages;

    public User() {
    }

    public User(int id, UserCreds userCreds, String firstName, String lastName,
                String phoneNumber, UserPhoto photoUrl, UserRole role, int rating) {
        this.userCreds = userCreds;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.photoUrl = photoUrl;
        this.role = role;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserCreds getUserCreds() {
        return userCreds;
    }

    public void setUserCreds(UserCreds userCreds) {
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

    public UserPhoto getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(UserPhoto photoUrl) {
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

    public List<SecretCode> getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(List<SecretCode> secretCode) {
        this.secretCode = secretCode;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Chat> getChats() {
        return chats;
    }

    public void setChats(Set<Chat> chats) {
        this.chats = chats;
    }

    public Set<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", photoUrl='" + photoUrl + '\'' +
                ", role=" + role +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                phoneNumber.equals(user.phoneNumber) &&
                rating.equals(user.rating) &&
                userCreds.equals(user.userCreds) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                Objects.equals(photoUrl, user.photoUrl) &&
                role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userCreds, firstName, lastName, phoneNumber, photoUrl, role, rating);
    }
}
