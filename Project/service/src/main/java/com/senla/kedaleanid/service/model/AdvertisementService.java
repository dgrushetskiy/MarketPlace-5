package com.senla.kedaleanid.service.model;

import com.senla.kedaleanid.dalapi.model.IAdvertisementCategoryDao;
import com.senla.kedaleanid.dalapi.model.IAdvertisementDao;
import com.senla.kedaleanid.dalapi.model.IAdvertisementPaidUntilDao;
import com.senla.kedaleanid.dalapi.model.IAdvertisementTypeDao;
import com.senla.kedaleanid.dto.IModelDto;
import com.senla.kedaleanid.dto.advertisement.advertisement.AdvertisementDto;
import com.senla.kedaleanid.dto.advertisement.advertisement.AdvertisementExtendedDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementCategoryDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementFilterDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementPhotoDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementTypeDto;
import com.senla.kedaleanid.model.advertisement.Advertisement;
import com.senla.kedaleanid.model.advertisement.AdvertisementCategory;
import com.senla.kedaleanid.model.advertisement.AdvertisementPaidUntil;
import com.senla.kedaleanid.model.advertisement.AdvertisementPhoto;
import com.senla.kedaleanid.model.advertisement.AdvertisementState;
import com.senla.kedaleanid.model.advertisement.AdvertisementType;
import com.senla.kedaleanid.service.AbstractService;
import com.senla.kedaleanid.serviceapi.model.IAdvertisementPhotoService;
import com.senla.kedaleanid.serviceapi.model.IAdvertisementService;
import com.senla.kedaleanid.utility.exception.NoDbRecordException;
import com.senla.kedaleanid.utility.exception.TransactionFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by earthofmarble on Sep, 2019
 */

@Service
public class AdvertisementService extends AbstractService<Advertisement, Integer>
        implements IAdvertisementService {

    private final IAdvertisementDao advertisementDao;
    private final IAdvertisementPaidUntilDao paidUntilDao;
    private final IAdvertisementPhotoService photoService;
    private final IAdvertisementCategoryDao categoryDao;
    private final IAdvertisementTypeDao typeDao;

    @Autowired
    public AdvertisementService(IAdvertisementDao daoObject, IAdvertisementPaidUntilDao paidUntilDao,
                                IAdvertisementPhotoService photoService, IAdvertisementCategoryDao categoryDao,
                                IAdvertisementTypeDao typeDao) {
        this.advertisementDao = daoObject;
        this.paidUntilDao = paidUntilDao;
        this.photoService = photoService;
        this.categoryDao = categoryDao;
        this.typeDao = typeDao;
        super.dataAccessObject = this.advertisementDao;
    }

    public IAdvertisementDao getAdvertisementDao() {
        return advertisementDao;
    }

    private void deleteAdPhotos(AdvertisementExtendedDto advDto) {
        List<AdvertisementPhotoDto> photoDtos = advDto.getAdPhotoUrl();
        if (photoDtos != null && !photoDtos.isEmpty()) {
            photoService.deletePhotos(advDto.getId());
        }
    }

    private void setTypeAndCategory(AdvertisementExtendedDto advDto, Advertisement model) {
        if (advDto.getAdCategory() != null) {
            AdvertisementCategory adCategory = categoryDao.readById(advDto.getAdCategory().getId());
            model.setAdCategory(adCategory);
            advDto.setAdCategory(null);
        }
        if (advDto.getAdType() != null) {
            AdvertisementType adType = typeDao.readById(advDto.getAdType().getId());
            model.setAdType(adType);
            advDto.setAdType(null);
        }
    }

    private void savePhotos(List<AdvertisementPhoto> photos, Advertisement advertisement) {
        if (photos != null && !photos.isEmpty()) {
            for (AdvertisementPhoto photo : photos) {
                photo.setAdId(advertisement.getAdId());
            }
            advertisement.setAdPhotos(photos);
            advertisementDao.update(advertisement);
        }
    }

    private void savePaidUntil(AdvertisementPaidUntil paidUntil, Advertisement advertisement) {
        if (paidUntil != null) {
            paidUntil.setAdId(advertisement.getAdId());
            paidUntilDao.save(paidUntil);
        }
    }

    private List<AdvertisementCategory> fillFilterCategories(AdvertisementFilterDto advFilter) {
        List<AdvertisementCategory> adCategories = new ArrayList<>();
        if (advFilter.getCategoryList() != null) {
            for (AdvertisementCategoryDto categoryDto : advFilter.getCategoryList()) {
                adCategories.add((AdvertisementCategory) mapper.convert(categoryDto, AdvertisementCategory.class, null));
            }
        }
        return adCategories;
    }

    private List<AdvertisementType> fillFilterTypes(AdvertisementFilterDto advFilter) {
        List<AdvertisementType> adTypes = new ArrayList<>();
        if (advFilter.getTypeList() != null) {
            for (AdvertisementTypeDto typeDto : advFilter.getTypeList()) {
                adTypes.add((AdvertisementType) mapper.convert(typeDto, AdvertisementType.class, null));
            }
        }
        return adTypes;
    }

    private List<AdvertisementDto> getFilteredAdDtos(AdvertisementFilterDto advFilter, List<AdvertisementCategory> adCategories,
                                                     List<AdvertisementType> adTypes, int firstElementPos, int pageSize) {
        List<AdvertisementDto> advDtos = new ArrayList<>();
        for (Advertisement adv : advertisementDao.getAdsWithFilter(advFilter.getName(), adCategories,
                adTypes, advFilter.getMinPrice(),
                advFilter.getMaxPrice(), firstElementPos, pageSize)) {
            advDtos.add((AdvertisementDto) mapper.convert(adv, AdvertisementDto.class, null));
        }
        return advDtos;
    }

    @Transactional
    public boolean update(IModelDto object) {
        AdvertisementExtendedDto advDto = (AdvertisementExtendedDto) object;
        tryNullDtoId(advDto);
        try {
            Advertisement model = advertisementDao.readById(advDto.getId());
            tryNullDto(model, "There is no " + Advertisement.class.getSimpleName() + " with id: " + advDto.getId());
            deleteAdPhotos(advDto);
            setTypeAndCategory(advDto, model);
            model = (Advertisement) mapper.convert(advDto, Advertisement.class, model);
            advertisementDao.update(model);
        } catch (Exception e) {
            throw new TransactionFailureException("Transaction failed!\n" + e.getMessage());
        }
        return true;
    }

    @Transactional
    public boolean changeState(Integer adId, AdvertisementState adState) {
        if (advertisementDao.readById(adId) == null) {
            throw new NoDbRecordException("There is no advertisement with id [" + adId + "]");
        }
        return advertisementDao.changeState(adId, adState);
    }

    @Transactional
    public List<AdvertisementDto> getAdsByState(Integer ownerId, int firstElement, int pageSize, AdvertisementState state) {
        List<Advertisement> advertisements = advertisementDao.getUserAdsByState(ownerId, firstElement, pageSize, state);
        List<AdvertisementDto> advDtos = new ArrayList<>();

        for (Advertisement advertisement : advertisements) {
            advDtos.add((AdvertisementDto) mapper.convert(advertisement, AdvertisementDto.class, null));
        }

        if (advDtos.isEmpty()) {
            advDtos.add(new AdvertisementDto());
        }

        return advDtos;
    }

    @Override
    @Transactional
    public List<AdvertisementDto> getAdsWithFilter(AdvertisementFilterDto advFilter, int firstElementPos, int pageSize) {
        paidUntilDao.removeExpired();

        List<AdvertisementCategory> adCategories = fillFilterCategories(advFilter);
        List<AdvertisementType> adTypes = fillFilterTypes(advFilter);

        return getFilteredAdDtos(advFilter, adCategories, adTypes, firstElementPos, pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(IModelDto object) {
        AdvertisementExtendedDto advDto = (AdvertisementExtendedDto) object;
        try {
            Advertisement advertisement = new Advertisement();
            advertisement = (Advertisement) mapper.convert(advDto, Advertisement.class, advertisement);
            AdvertisementPaidUntil paidUntil = null;
            if (advertisement.getPaidUntil() != null) {
                paidUntil = new AdvertisementPaidUntil();
                paidUntil.setPaidUntil(advertisement.getPaidUntil().getPaidUntil());
                advertisement.setPaidUntil(null);
            }
            List<AdvertisementPhoto> photos = advertisement.getAdPhotos();
            advertisement.setAdPhotos(null);
            advertisementDao.save(advertisement);
            savePhotos(photos, advertisement);
            savePaidUntil(paidUntil, advertisement);
        } catch (Exception e) {
            throw new TransactionFailureException("Transaction failed!\n" + e.getMessage());
        }
        return true;
    }
}
