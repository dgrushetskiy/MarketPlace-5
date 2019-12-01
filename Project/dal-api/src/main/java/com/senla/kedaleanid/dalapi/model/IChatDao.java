package com.senla.kedaleanid.dalapi.model;

import com.senla.kedaleanid.dalapi.IGenericDao;
import com.senla.kedaleanid.model.chat.Chat;

import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IChatDao extends IGenericDao<Chat, Integer> {

    List<Chat> getChatUsingUsers(int senderId, int recipientId);  //TODO rename me please

}
