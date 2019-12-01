package com.senla.kedaleanid.dalapi.model;

import com.senla.kedaleanid.dalapi.IGenericDao;
import com.senla.kedaleanid.model.chat.Message;

import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IMessageDao extends IGenericDao<Message, Integer> {

    List<Message> getChatMessages(int chatId, int firstElement, int pageSize, String messageText);

    List<Message> getUserChats(int userId, String chatTopic, int firstElement, int pageSize);

}
