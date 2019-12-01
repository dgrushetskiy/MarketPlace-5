package com.senla.kedaleanid.controller.restcontroller.chat;

import com.senla.kedaleanid.controller.configuration.security.principal.UserPrincipal;
import com.senla.kedaleanid.controller.restcontroller.AbstractController;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.chat.ChatDto;
import com.senla.kedaleanid.dto.chat.MessageDto;
import com.senla.kedaleanid.dto.userdto.user.UserDto;
import com.senla.kedaleanid.serviceapi.model.IChatService;
import com.senla.kedaleanid.serviceapi.model.IMessageService;
import com.senla.kedaleanid.utility.exception.NullDtoObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * Created by earthofmarble on Sep, 2019
 */
@RestController
@RequestMapping(path = "chats", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class ChatController extends AbstractController {

    private IChatService chatService;
    private IMessageService messageService;

    @Autowired
    public ChatController(IChatService chatService, IMessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    private void checkAuthority(Set<UserDto> users) {
        if (users == null) {
            throw new NullDtoObjectException("Null users collection received!");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        for (UserDto user : users) {
            if (user.getId().equals(principal.getId())) {
                return;
            }
        }
        throw new AccessDeniedException("Access Denied. Principal's id: [" + principal.getId() + "] clashes with given ids");
    }

    @GetMapping(value = "/{userId}")
    public List<MessageDto> getUserChats(@PathVariable(value = "userId") @Positive int userId,
                                         @RequestParam(value = "topic", required = false) @Size(min = 1, max = 20) String chatTopic,
                                         @RequestParam(value = "first", required = false) @PositiveOrZero Integer firstElement,
                                         @RequestParam(value = "size", required = false) @PositiveOrZero Integer pageSize) {

        Pagination pagination = configurePagination(firstElement, pageSize);
        List<IModelDto> list = messageService.getUserChats(userId, chatTopic, pagination.getFirstElement(),
                pagination.getPageSize(), MessageDto.class);
        return castModelDtoList(list, MessageDto.class);
    }

    @GetMapping(value = "/{ownerId}/{recipientId}")
    public List<MessageDto> getUserChats(@PathVariable(value = "ownerId") @Positive int userId,
                                         @PathVariable(value = "recipientId") @Positive int recipientId,
                                         @RequestParam(value = "text", required = false) @Size(min = 1, max = 40) String messageText,
                                         @RequestParam(value = "first", required = false) @PositiveOrZero Integer firstElement,
                                         @RequestParam(value = "size", required = false) @PositiveOrZero Integer pageSize) {

        Pagination pagination = configurePagination(firstElement, pageSize);
        List<IModelDto> list = messageService.getChatMessages(userId, recipientId,
                pagination.getFirstElement(), pagination.getPageSize(),
                messageText, MessageDto.class);
        return castModelDtoList(list, MessageDto.class);
    }

    @PostMapping(value = "/{ownerId}/{recipientId}/send-message", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean sendMessage(@RequestBody @Validated(value = MessageDto.SendMessageGroup.class) MessageDto object) {
        checkAuthority(object.getOwner().getId());
        return messageService.create(object);
    }

    @PostMapping(value = "/{ownerId}/start-chat", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean startChat(@RequestBody @Validated(value = ChatDto.StartChatGroup.class) ChatDto object) {
        checkAuthority(object.getUsers());
        return chatService.create(object);
    }

    @PutMapping(value = "/{ownerId}/{recipientId}/update-message", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean updateMessage(@RequestBody @Validated(value = MessageDto.UpdateMessageGroup.class) MessageDto object) {
        checkAuthority(object.getOwner().getId());
        return messageService.update(object);
    }

    @PutMapping(value = "/{ownerId}/{recipientId}/update-chat", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean updateChat(@RequestBody @Validated(value = ChatDto.UpdateChatGroup.class) ChatDto object) {
        checkAuthority(object.getUsers());
        return chatService.update(object);
    }

    @DeleteMapping(value = "/{ownerId}/{recipientId}/delete-message", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean deleteMessage(@RequestBody @Validated(MessageDto.DeleteMessageGroup.class) MessageDto object) {
        checkAuthority(object.getOwner().getId());
        return messageService.delete(object);
    }

    @DeleteMapping(value = "/{ownerId}/{recipientId}/delete-chat", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean deleteChat(@PathVariable(value = "ownerId") int ownerId, @PathVariable(value = "recipientId") int recipientId) {
        return chatService.delete(ownerId, recipientId);
    }
}
