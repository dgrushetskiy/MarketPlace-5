package com.senla.kedaleanid.serviceapi;

import com.senla.kedaleanid.dto.IModelDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IGenericService<T, PK extends Serializable> {

    IModelDto readByPrimaryKey(PK pk, Class convertToClazz);

    boolean create(IModelDto object);

    boolean update(IModelDto object);

    boolean delete(IModelDto object);

    List<IModelDto> readAllWithPagination(int firstElement, int pageSize, Class convertToClazz);
}
