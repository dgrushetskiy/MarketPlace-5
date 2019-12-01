package com.senla.kedaleanid.dalapi;

import java.io.Serializable;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */

public interface IGenericDao<T, PK extends Serializable> {

    T readById(PK primaryKey);

    void save(T object);

    void update(T object);

    void delete(T object);

    List<T> readAllWithPagination(int firstPos, int pageSize);

}
