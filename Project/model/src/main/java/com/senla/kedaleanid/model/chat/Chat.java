package com.senla.kedaleanid.model.chat;

import com.senla.kedaleanid.model.IModel;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;
import com.senla.kedaleanid.utility.mapper.enumeration.PropertyType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Entity(name = "Chat")
@Table(name = "chat", schema = "advertisements")
@NamedEntityGraphs({@NamedEntityGraph(name = "chatGraph",
        attributeNodes = {@NamedAttributeNode(value = "userList")})})
@Convertible
public class Chat implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id", updatable = false, nullable = false)
    @ReferencedField(name = "id")
    private Integer chatId;
    @Column(name = "chat_topic")
    private String chatTopic;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_chat",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_owner_id"))
    @ReferencedField(name = "userList", type = PropertyType.COLLECTION,
            thisCollectionType = HashSet.class, thisContainsClass = User.class)
    private Set<User> userList;                 //TODO MERGE REMOVEME
    @OneToMany(mappedBy = "chat", cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @OrderBy("date DESC")
    private Set<Message> messages;

    public Chat() {
    }

    public Chat(String chatTopic, Set<User> userList) {
        this.chatTopic = chatTopic;
        this.userList = userList;
    }

    public Chat(String chatTopic) {
        this.chatTopic = chatTopic;
    }

    public Set<Message> getMessages() {
        if (messages == null) {
            messages = new HashSet<>();
        }
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getChatTopic() {
        return chatTopic;
    }

    public void setChatTopic(String chatTopic) {
        this.chatTopic = chatTopic;
    }

    public Set<User> getUserList() {
        if (userList == null) {
            userList = new HashSet<>();
        }
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "chat{" +
                "chatId=" + chatId +
                ", chatTopic='" + chatTopic + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        Chat chat = (Chat) o;
        return Objects.equals(getChatId(), chat.getChatId()) &&
                getChatTopic().equals(chat.getChatTopic()) &&
                getUserList().equals(chat.getUserList()) &&
                Objects.equals(getMessages(), chat.getMessages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChatId(), getChatTopic(), getUserList());
    }
}
