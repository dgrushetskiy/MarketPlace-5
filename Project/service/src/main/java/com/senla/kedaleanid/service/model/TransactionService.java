package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.IAdvertisementDao;
import com.senla.kedaleanid.dalapi.model.ITransactionDao;
import com.senla.kedaleanid.dalapi.model.IUserDao;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.transaction.AdvTransactionDto;
import com.senla.kedaleanid.model.advertisement.Advertisement;
import com.senla.kedaleanid.model.transaction.Transaction;
import com.senla.kedaleanid.model.user.User;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.ITransactionService;
import com.senla.kedaleanid.utility.exception.TransactionFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Service
public class TransactionService extends AbstractService<Transaction, Integer>
        implements ITransactionService {

    private ITransactionDao transactionDao;
    private IUserDao userDao;
    private IAdvertisementDao advertisementDao;

    @Autowired
    public TransactionService(ITransactionDao daoObject, IUserDao userDao, IAdvertisementDao advertisementDao) {
        this.transactionDao = daoObject;
        this.userDao = userDao;
        this.advertisementDao = advertisementDao;
        super.dataAccessObject = this.transactionDao;
    }

    public ITransactionDao getTransactionDao() {
        return transactionDao;
    }

    private void saveTransaction(AdvTransactionDto transactionDto, User user) {
        try {
            transactionDto.setOwner(null);
            transactionDto.setAdvertisement(null);
            Transaction transaction = (Transaction) mapper.convert(transactionDto, Transaction.class, null);
            transaction.setOwner(user);
            transactionDao.save(transaction);
        } catch (Exception e) {
            throw new TransactionFailureException("Transaction committing failed!\n" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean create(IModelDto object) {
        AdvTransactionDto transactionDto = (AdvTransactionDto) object;
        if (transactionDto.getAdvertisement().getId() == null) {
            return false;
        }

        User user;
        Advertisement advertisement;
        Integer userId = transactionDto.getOwner().getId();
        Integer advId = transactionDto.getAdvertisement().getId();

        tryNullDto(userId, "AdvTransactionDto.owner.id is null");
        tryNullDto(advId, "AdvTransactionDto.advertisement.id is null");

        user = userDao.readById(userId);
        advertisement = advertisementDao.readById(advId);

        tryNullDbRecord(user, "User with id [" + userId + "] doesn't exist (" + user + ")");
        tryNullDbRecord(advertisement, "Advertisement with id [" + advId + "] doesn't exist (" + advertisement + ")");

        saveTransaction(transactionDto, user);

        return true;

    }

}
