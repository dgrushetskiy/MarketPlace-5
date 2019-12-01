package com.senla.kedaleanid.controller.restcontroller.advertisement;

import com.senla.kedaleanid.controller.restcontroller.AbstractController;
import com.senla.kedaleanid.dto.advertisement.advertisement.AdvertisementDto;
import com.senla.kedaleanid.dto.advertisement.advertisement.AdvertisementExtendedDto;
import com.senla.kedaleanid.dto.advertisement.advertisementSecondary.AdvertisementFilterDto;
import com.senla.kedaleanid.serviceapi.model.IAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * Created by earthofmarble on Oct, 2019
 */
@RestController
@RequestMapping(value = "/ads/", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        consumes = {MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class AdvertisementController extends AbstractController {

    private IAdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(IAdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping
    public List<AdvertisementDto> getAds(@RequestParam(value = "fa", required = false) Integer firstAdv,
                                         @RequestParam(value = "sa", required = false) Integer advPageSize,
                                         @RequestBody(required = false) @Validated(value = AdvertisementFilterDto.AdvFilterGroup.class)
                                                 AdvertisementFilterDto advFilter) {
        Pagination pagination = configurePagination(firstAdv, advPageSize);
        if (advFilter == null) {
            advFilter = new AdvertisementFilterDto();
        }
        return advertisementService.getAdsWithFilter(advFilter, pagination.getFirstElement(), pagination.getPageSize());
    }

    @GetMapping(value = "/{id}")
    public AdvertisementExtendedDto getById(@PathVariable(value = "id") int id) {
        AdvertisementExtendedDto advertisementExtendedDto = (AdvertisementExtendedDto) advertisementService
                .readByPrimaryKey(id, AdvertisementExtendedDto.class);
        return Objects.requireNonNullElseGet(advertisementExtendedDto, AdvertisementExtendedDto::new);
    }

    @PostMapping(value = "/create-ad", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean createAd(@RequestBody @Validated(value = AdvertisementExtendedDto.CreateAdGroup.class)
                                    AdvertisementExtendedDto object) {
        return advertisementService.create(object);
    }

    @PutMapping(value = "/edit-ad", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean editAd(@RequestBody @Validated(value = AdvertisementExtendedDto.EditAdGroup.class)
                                  AdvertisementExtendedDto object) {
        checkAuthority(object.getOwner().getId());
        return advertisementService.update(object);
    }

    @DeleteMapping(value = "/delete-ad", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public boolean deleteAd(@RequestBody @Validated(value = AdvertisementExtendedDto.DeleteAdGroup.class)
                                    AdvertisementExtendedDto object) {
        checkAuthority(object.getOwner().getId());
        return advertisementService.delete(object);
    }
}
