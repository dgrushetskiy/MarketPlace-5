package com.senla.kedaleanid.serviceapi.model;

import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.model.chat.Message;
import com.senla.kedaleanid.serviceapi.IGenericService;

import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IMessageService extends IGenericService<Message, Integer> {

    List<IModelDto> getChatMessages(int senderId, int recipientId,
                                    int firstElement, int pageSize,
                                    String messageText, Class convertToClazz);

    List<IModelDto> getUserChats(int userId, String chatTopic, int firstElement, int pageSize, Class convertToClazz);

}
