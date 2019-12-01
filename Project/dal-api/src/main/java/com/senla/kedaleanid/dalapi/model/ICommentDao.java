package com.senla.kedaleanid.dalapi.model;

import com.senla.kedaleanid.dalapi.IGenericDao;
import com.senla.kedaleanid.model.advertisement.Comment;

import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface ICommentDao extends IGenericDao<Comment, Integer> {

    List<Comment> getAdComments(int adId, int firstElement, int pageSize);

}
