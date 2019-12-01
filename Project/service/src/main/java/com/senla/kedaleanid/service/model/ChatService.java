package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.IChatDao;
import com.senla.kedaleanid.dalapi.model.IUserDao;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.chat.ChatDto;
import com.senla.kedaleanid.dto.userdto.user.UserDto;
import com.senla.kedaleanid.model.chat.Chat;
import com.senla.kedaleanid.model.chat.Message;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.IChatService;
import com.senla.kedaleanid.utility.exception.InvalidRecordAmountReturnedException;
import com.senla.kedaleanid.utility.exception.RecordAlreadyExistsException;
import com.senla.kedaleanid.utility.exception.TransactionFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Service
public class ChatService extends AbstractService<Chat, Integer> implements IChatService {

    private IChatDao chatDao;
    private IUserDao userDao;

    @Autowired
    public ChatService(IChatDao daoObject, IUserDao userDao) {
        this.chatDao = daoObject;
        this.userDao = userDao;
        super.dataAccessObject = this.chatDao;
    }

    public IChatDao getChatDao() {
        return chatDao;
    }

    private List<Integer> chatChecks(ChatDto chatDto) {
        if (chatDto.getUsers().size() != 2) {
            throw new TransactionFailureException("Chat.users().size=" + chatDto.getUsers().size() + ". Must be 2!");
        }

        List<Integer> list = new ArrayList<>();
        for (UserDto user : chatDto.getUsers()) {
            list.add(user.getId());
        }

        if (list.get(0).equals(list.get(1))) {
            throw new TransactionFailureException("Both users have the same ID! [" + list.get(0) + "] & [" + list.get(1) + "]");
        }
        return list;
    }


    private Message getMessageAndFillUsers(List<Integer> idList, Set<User> users,
                                           Integer firstMessageOwnerId, ChatDto chatDto) {
        Message message = null;
        for (int i = 0; i < 2; i++) {
            User user = userDao.readById(idList.get(i));
            tryNullDto(user, "There is no user with id [" + idList.get(i) + "]");
            if (firstMessageOwnerId.equals(idList.get(i))) {
                message = (Message) mapper.convert(chatDto.getFirstMessage(), Message.class, null);
                message.setOwner(user);
            }
            users.add(user);
        }
        return message;
    }

    private void createLogic(ChatDto chatDto, List<Integer> idList) {
        try {
            Set<User> users = new HashSet<>();
            Integer firstMessageOwnerId = chatDto.getFirstMessage().getOwner().getId();
            Message message = getMessageAndFillUsers(idList, users, firstMessageOwnerId, chatDto);
            tryNullDto(message, "Null firstMessage. Wrong owner id!");
            saveChatAndMessage(chatDto, users, message);
        } catch (Exception e) {
            throw new TransactionFailureException("Transaction failed! \n" + e.getMessage());
        }
    }

    private void saveChatAndMessage(ChatDto chatDto, Set<User> users, Message message) {
        Chat model = (Chat) mapper.convert(chatDto, Chat.class, null);
        model.setUserList(users);
        chatDao.save(model);

        message.setChat(model);

        model.setMessages(new HashSet<>());
        model.getMessages().add(message);

        chatDao.update(model);
    }

    private void tryChatExistence(List<Integer> list) {
        if (getChatUsingUsers(list.get(0), list.get(1)) != null) {
            throw new RecordAlreadyExistsException("Chat between users [" + list.get(0) + "] & [" + list.get(1) + "] already exists!");
        }
    }

    @Transactional
    public Integer getChatUsingUsers(int senderId, int recipientId) {
        List<Chat> chats = chatDao.getChatUsingUsers(senderId, recipientId);
        if (chats == null || chats.size() == 0) {
            return null;
        } else if (chats.size() > 1) {
            throw new InvalidRecordAmountReturnedException(chats.size() + " [chat] records returned from DB, expected 1. " +
                    "Seems like you have more than 1 chat for 2 same users in your data base");
        } else {
            return chats.get(0).getChatId();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean create(IModelDto object) {
        tryNullDto(object, "Null DTO. Create() on " + this.getClass().getSimpleName());
        ChatDto chatDto = (ChatDto) object;
        List<Integer> list = chatChecks(chatDto);
        tryChatExistence(list);
        createLogic(chatDto, list);

        return true;
    }

    @Transactional
    public boolean delete(int ownerId, int recipientId) {
        Integer chatId = getChatUsingUsers(ownerId, recipientId);
        tryNullDbRecord(chatId, "There is no chat between users [" + ownerId + "] & [" + recipientId + "]");
        try {
            Chat model = chatDao.readById(chatId);
            if (model != null) {
                chatDao.delete(model);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new TransactionFailureException("Transaction committing failed! \n" + e.getMessage());
        }
    }

}
