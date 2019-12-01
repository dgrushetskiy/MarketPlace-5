package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.IMessageDao;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.chat.MessageDto;
import com.senla.kedaleanid.model.chat.Message;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.IChatService;
import com.senla.kedaleanid.serviceapi.model.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Service
public class MessageService extends AbstractService<Message, Integer>
        implements IMessageService {

    private IMessageDao messageDao;
    private IChatService chatService;

    @Autowired
    public MessageService(IMessageDao daoObject, IChatService chatService) {
        this.messageDao = daoObject;
        this.chatService = chatService;
        super.dataAccessObject = this.messageDao;
    }

    public IMessageDao getMessageDao() {
        return messageDao;
    }

    @Transactional
    public List<IModelDto> getChatMessages(int senderId, int recipientId, int firstElement,
                                           int pageSize, String messageText, Class convertToClazz) {
        Integer chatId = chatService.getChatUsingUsers(senderId, recipientId);
        tryNullDbRecord(chatId, "There is no chat between users: [" + senderId + "] and [" + recipientId + "]!");

        List<IModelDto> messageDtos = convertListToDto(messageDao.getChatMessages(chatId, firstElement,
                pageSize, messageText), convertToClazz);
        addOneIfEmpty(messageDtos, MessageDto.class);

        return messageDtos;
    }

    @Transactional
    public List<IModelDto> getUserChats(int userId, String chatTopic, int firstElement,
                                        int pageSize, Class convertToClazz) {
        List<Message> chats = messageDao.getUserChats(userId, chatTopic, firstElement, pageSize);
        return convertListToDto(chats, convertToClazz);
    }

}
