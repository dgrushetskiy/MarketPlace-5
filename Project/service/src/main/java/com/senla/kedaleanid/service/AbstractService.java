package com.senla.kedaleanid.service;

import com.senla.kedaleanid.dalapi.IGenericDao;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.model.IModel;
import com.senla.kedaleanid.serviceapi.IGenericService;
import com.senla.kedaleanid.utility.exception.ClassConstructorException;
import com.senla.kedaleanid.utility.exception.InvocationException;
import com.senla.kedaleanid.utility.exception.NoDbRecordException;
import com.senla.kedaleanid.utility.exception.NullDtoObjectException;
import com.senla.kedaleanid.utility.exception.TransactionFailureException;
import com.senla.kedaleanid.utility.mapper.service.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */
@SuppressWarnings("unchecked")
@Service
public abstract class AbstractService<T extends IModel, PK extends Serializable>
        implements IGenericService<T, PK> {

    @Autowired
    protected Mapper mapper;

    protected IGenericDao dataAccessObject;
    private Logger logger = LoggerFactory.getLogger(AbstractService.class);

    protected AbstractService() {
    }

    private Class<T> getEntityType() {
        return ((Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0]);
    }

    private IModelDto createNewInstanceByClassName(Class clazz) {
        try {
            return (IModelDto) clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new ClassConstructorException("Couldn't load constructor for class " + clazz.getName() + "\n" + e.getMessage());
        } catch (InstantiationException e) {
            throw new ClassConstructorException("Couldn't use constructor of class: " + clazz.getName() +
                    ". Maybe class has no nullable constructor or given class is an interface/abstract/primitive" +
                    "\n" + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ClassConstructorException("Access to constructor of " + clazz.getName() + " restricted" + "\n" + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new InvocationException("InvocationTargetException :) Object create failed! \n" + e.getMessage());
        }
    }

    protected List<IModelDto> convertListToDto(List<T> list, Class clazz) {
        List<IModelDto> dtoList = new ArrayList<>();

        if (list == null) {
            logger.info("An attempt to convert an empty list");
            return null;
        }

        if (list.size() == 0) {
            dtoList.add(createNewInstanceByClassName(clazz));
        } else {
            for (T element : list) {
                dtoList.add(convertSingleModelToDto(element, clazz));
            }
        }
        return dtoList;
    }

    protected IModelDto convertSingleModelToDto(IModel model, Class clazz) {
        return (IModelDto) mapper.convert(model, clazz, null);
    }

    protected void addOneIfEmpty(List<IModelDto> dtos, Class clazz) {
        if (dtos == null || dtos.isEmpty()) {
            dtos = new ArrayList<>();
            dtos.add(createNewInstanceByClassName(clazz));
        }
    }

    protected void tryNullDto(Object object, String message) {
        if (object == null) {
            throw new NullDtoObjectException(message);
        }
    }

    protected void tryNullDbRecord(Object object, String message) {
        if (object == null) {
            throw new NoDbRecordException(message);
        }
    }

    protected void tryNullDtoId(IModelDto object) {
        tryNullDto(object, "DTO value wasn't initialized");
        if (object.getId() == null) {
            throw new NullDtoObjectException("An error occurred while trying to save object. " +
                    "DTO value wasn't initialized (DTO == null). create() on " + this.getClass().getSimpleName());
        }
    }

    @Transactional
    public IModelDto readByPrimaryKey(PK pk, Class convertToClazz) {
        T model = (T) dataAccessObject.readById(pk);
        if (model == null) {
            throw new NoDbRecordException("No [" + getEntityType() + "] with id: [" + pk + "]");
        }
        return convertSingleModelToDto(model, convertToClazz);
    }

    @Transactional
    public boolean create(IModelDto object) {
        Class clazz = getEntityType();
        tryNullDto(object, "Null DTO create() on " + this.getClass().getSimpleName());
        try {
            IModel model = (IModel) clazz.getDeclaredConstructor().newInstance();
            model = (IModel) mapper.convert(object, clazz, model);
            dataAccessObject.save(model);
        } catch (Exception e) {
            throw new TransactionFailureException("Transaction failed! \n" + e.getMessage());
        }

        return true;
    }

    @Transactional
    public boolean update(IModelDto object) {
        Class clazz = getEntityType();
        tryNullDtoId(object);
        IModel model = (IModel) dataAccessObject.readById(object.getId());
        tryNullDbRecord(model, "There is no " + clazz.getSimpleName() + " with id: " + object.getId());

        try {
            model = (IModel) mapper.convert(object, clazz, model);
            dataAccessObject.update(model);
        } catch (Exception e) {
            throw new TransactionFailureException("Transaction committing failed! \n" + e.getMessage());
        }

        return true;
    }

    @Transactional
    public boolean delete(IModelDto object) {
        tryNullDtoId(object);
        try {
            Integer objId = object.getId();
            IModel model = (IModel) dataAccessObject.readById(objId);
            if (model != null) {
                dataAccessObject.delete(model);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new TransactionFailureException("Transaction committing failed! \n" + e.getMessage());
        }
    }

    @Transactional
    public List<IModelDto> readAllWithPagination(int firstElementPos, int pageSize, Class convertToClazz) {
        List<T> list = (List<T>) dataAccessObject.readAllWithPagination(firstElementPos, pageSize);
        return convertListToDto(list, convertToClazz);
    }
}
