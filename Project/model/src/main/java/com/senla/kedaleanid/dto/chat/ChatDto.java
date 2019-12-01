package com.senla.kedaleanid.dto.chat;

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
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Convertible
public class ChatDto implements IModelDto {
    @ReferencedField(name = "chatId")
    @NotNull(groups = {MessageDto.SendMessageGroup.class, UpdateChatGroup.class, DeleteChatGroup.class})
    @Null(groups = StartChatGroup.class)
    @Positive
    private Integer id;
    @NotNull(groups = StartChatGroup.class)
    private String chatTopic;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ReferencedField(name = "userList", type = PropertyType.COLLECTION,
            thisCollectionType = HashSet.class, thisContainsClass = UserDto.class)
    @Valid
    @NotNull(groups = {StartChatGroup.class, UpdateChatGroup.class})
    @Size(min = 2, max = 2, groups = {UpdateChatGroup.class, StartChatGroup.class, DeleteChatGroup.class})
    private Set<UserDto> users;
    @NotNull(groups = StartChatGroup.class)
    @Valid
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private MessageDto firstMessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChatTopic() {
        return chatTopic;
    }

    public void setChatTopic(String chatTopic) {
        this.chatTopic = chatTopic;
    }

    public Set<UserDto> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDto> users) {
        this.users = users;
    }

    public MessageDto getFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(MessageDto firstMessage) {
        this.firstMessage = firstMessage;
    }

    public interface StartChatGroup {
    }

    public interface UpdateChatGroup {
    }

    public interface DeleteChatGroup {
    }
}
