package com.senla.kedaleanid.serviceapi.model;

import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.model.advertisement.Comment;
import com.senla.kedaleanid.serviceapi.IGenericService;

import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface ICommentService extends IGenericService<Comment, Integer> {

    List<IModelDto> getAdComments(int adId, int firstElement, int pageSize, Class convertToClazz);

}
