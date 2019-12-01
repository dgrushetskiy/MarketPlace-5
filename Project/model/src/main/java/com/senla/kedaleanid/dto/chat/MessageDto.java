package com.senla.kedaleanid.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.userdto.user.UserDto;
import com.senla.kedaleanid.utility.mapper.annotation.Convertible;
import com.senla.kedaleanid.utility.mapper.annotation.ReferencedField;
import com.senla.kedaleanid.utility.mapper.enumeration.PropertyType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Convertible
public class MessageDto implements IModelDto {
    @ReferencedField(name = "messageId")
    @Null(groups = {SendMessageGroup.class, ChatDto.StartChatGroup.class})
    @NotNull(groups = {UpdateMessageGroup.class, DeleteMessageGroup.class})
    @Positive
    private Integer id;
    @ReferencedField(type = PropertyType.COMPOSITE)
    @Valid
    @NotNull(groups = {SendMessageGroup.class, UpdateMessageGroup.class, DeleteMessageGroup.class, ChatDto.StartChatGroup.class})
    private UserDto owner;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ReferencedField(type = PropertyType.COMPOSITE)
    @Valid
    @NotNull(groups = SendMessageGroup.class)
    @Null(groups = {UpdateMessageGroup.class, DeleteMessageGroup.class, ChatDto.StartChatGroup.class})
    private ChatDto chat;
    @NotNull(groups = {SendMessageGroup.class, ChatDto.StartChatGroup.class})
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @NotNull(groups = {SendMessageGroup.class, ChatDto.StartChatGroup.class})
    @Null(groups = {UpdateMessageGroup.class, DeleteMessageGroup.class})
    private Timestamp date;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public ChatDto getChat() {
        return chat;
    }

    public void setChat(ChatDto chat) {
        this.chat = chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public interface SendMessageGroup {
    }

    public interface UpdateMessageGroup {
    }

    public interface DeleteMessageGroup {
    }
}
