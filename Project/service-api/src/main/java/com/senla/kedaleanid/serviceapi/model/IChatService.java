package com.senla.kedaleanid.serviceapi.model;

import com.senla.kedaleanid.model.chat.Chat;
import com.senla.kedaleanid.serviceapi.IGenericService;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IChatService extends IGenericService<Chat, Integer> {

    Integer getChatUsingUsers(int senderId, int recipientId);  //TODO rename me please

    boolean delete(int ownerId, int recipientId);

}
