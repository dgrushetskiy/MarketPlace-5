package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.IAdvertisementPaidUntilDao;
import com.senla.kedaleanid.dto.transaction.AdvTransactionDto;
import com.senla.kedaleanid.model.advertisement.AdvertisementPaidUntil;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.IAdvertisementPaidUntilService;
import com.senla.kedaleanid.serviceapi.model.ITransactionService;
import com.senla.kedaleanid.utility.exception.NullDtoObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Service
public class AdvertisementPaidUntilService extends AbstractService<AdvertisementPaidUntil, Integer>
        implements IAdvertisementPaidUntilService {

    private IAdvertisementPaidUntilDao paidUntilDao;
    private ITransactionService transactionService;

    @Autowired
    public AdvertisementPaidUntilService(IAdvertisementPaidUntilDao daoObject, ITransactionService transactionService) {
        this.paidUntilDao = daoObject;
        this.transactionService = transactionService;
        super.dataAccessObject = this.paidUntilDao;
    }

    public IAdvertisementPaidUntilDao getPaidUntilDao() {
        return paidUntilDao;
    }

    private void existsLogic(AdvertisementPaidUntil paidUntil, AdvTransactionDto advTransactionDto) {
        paidUntil.getPaidUntil().setTime(paidUntil.getPaidUntil().getTime() + (advTransactionDto.getHours() * 3600000));
        paidUntilDao.update(paidUntil);
    }

    private void doesntExistLogic(AdvertisementPaidUntil paidUntil, LocalDateTime now, AdvTransactionDto advTransactionDto) {
        Timestamp timestamp = Timestamp.valueOf(now);
        timestamp.setTime(timestamp.getTime() + (advTransactionDto.getHours() * 3600000));
        paidUntil = new AdvertisementPaidUntil();
        paidUntil.setAdId(advTransactionDto.getAdvertisement().getId());
        paidUntil.setPaidUntil(timestamp);
        paidUntilDao.save(paidUntil);
    }

    @Transactional
    public boolean payAd(AdvTransactionDto advTransactionDto) {
        if (advTransactionDto != null && advTransactionDto.getAdvertisement().getId() != null
                && advTransactionDto.getOwner().getId() != null) {
            LocalDateTime now = LocalDateTime.now();
            AdvertisementPaidUntil paidUntil = paidUntilDao.readById(advTransactionDto.getAdvertisement().getId());

            if (paidUntil != null) {
                existsLogic(paidUntil, advTransactionDto);
            } else {
                doesntExistLogic(paidUntil, now, advTransactionDto);
            }

            advTransactionDto.setDate(Timestamp.valueOf(now));
            transactionService.create(advTransactionDto);
            return true;
        }
        throw new NullDtoObjectException("Couldn't pay ad. AdvTransactionDto fields haven't been initialized");
    }

}
