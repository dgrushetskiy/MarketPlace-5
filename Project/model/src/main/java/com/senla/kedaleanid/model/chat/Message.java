package com.senla.kedaleanid.model.chat;

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

@Entity(name = "Message")
@Table(name = "message", schema = "advertisements")
@NamedEntityGraph(name = "messageGraph",
        attributeNodes = {@NamedAttributeNode(value = "chat", subgraph = "chatGraph"),
                @NamedAttributeNode(value = "owner", subgraph = "userGraph")},
        subgraphs = {@NamedSubgraph(name = "userGraph",
                attributeNodes = @NamedAttributeNode(value = "photoUrl")),
                @NamedSubgraph(name = "chatGraph",
                        attributeNodes = @NamedAttributeNode(value = "userList", subgraph = "userGraph"))}
)
@Convertible
public class Message implements IModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", updatable = false, nullable = false)
    private Integer messageId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_owner_id")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private User owner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_chat_id")
    @ReferencedField(type = PropertyType.COMPOSITE)
    private Chat chat;
    @Column(name = "message_text")
    private String text;
    @Column(name = "message_date")
    private java.sql.Timestamp date;

    public Message() {
    }

    public Message(User owner, Chat chat, String text, java.sql.Timestamp date) {
        this.owner = owner;
        this.chat = chat;
        this.text = text;
        this.date = date;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public java.sql.Timestamp getDate() {
        return date;
    }

    public void setDate(java.sql.Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", owner=" + owner +
                ", chat=" + chat +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getMessageId().equals(message.getMessageId()) &&
                getOwner().equals(message.getOwner()) &&
                getChat().equals(message.getChat()) &&
                getText().equals(message.getText()) &&
                getDate().equals(message.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessageId(), getOwner(), getChat(), getText(), getDate());
    }
}
