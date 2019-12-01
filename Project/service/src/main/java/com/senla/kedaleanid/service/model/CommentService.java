package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.ICommentDao;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.model.advertisement.Comment;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Service
public class CommentService extends AbstractService<Comment, Integer>
        implements ICommentService {

    private ICommentDao commentDao;

    @Autowired
    public CommentService(ICommentDao daoObject) {
        this.commentDao = daoObject;
        super.dataAccessObject = this.commentDao;
    }

    public ICommentDao getCommentDao() {
        return commentDao;
    }

    public List<IModelDto> getAdComments(int adId, int firstElement,
                                         int pageSize, Class convertToClazz) {
        List<Comment> comments = commentDao.getAdComments(adId, firstElement, pageSize);
        if (comments == null || comments.isEmpty()) {
            comments = new ArrayList<>();
        }
        return convertListToDto(comments, convertToClazz);
    }

}
