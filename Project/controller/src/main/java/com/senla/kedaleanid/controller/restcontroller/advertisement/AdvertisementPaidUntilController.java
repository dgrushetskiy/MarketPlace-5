package com.senla.kedaleanid.controller.restcontroller.advertisement;

import com.senla.kedaleanid.controller.restcontroller.AbstractController;
import com.senla.kedaleanid.dto.transaction.AdvTransactionDto;
import com.senla.kedaleanid.serviceapi.model.IAdvertisementPaidUntilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by earthofmarble on Oct, 2019
 */
@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class AdvertisementPaidUntilController extends AbstractController {

    private IAdvertisementPaidUntilService painUntilService;

    @Autowired
    AdvertisementPaidUntilController(IAdvertisementPaidUntilService painUntilService) {
        this.painUntilService = painUntilService;
    }

    @PostMapping(value = "/pay-ad")
    public boolean payAd(@RequestBody @Validated(AdvTransactionDto.PayAdGroup.class) AdvTransactionDto object) {
        checkAuthority(object.getOwner().getId());
        return painUntilService.payAd(object);
    }

}
