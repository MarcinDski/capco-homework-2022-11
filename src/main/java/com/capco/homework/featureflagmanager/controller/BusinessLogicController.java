package com.capco.homework.featureflagmanager.controller;

import com.capco.homework.featureflagmanager.api.business.BusinessApi;
import com.capco.homework.featureflagmanager.api.business.model.BusinessDataDto;
import com.capco.homework.featureflagmanager.db.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static com.capco.homework.featureflagmanager.api.admin.model.Feature.ELABORATED_REPORTS;
import static com.capco.homework.featureflagmanager.api.admin.model.Feature.FANCY_GREETING;
import static com.capco.homework.featureflagmanager.api.business.model.Constants.*;

@RestController
@RequiredArgsConstructor
public class BusinessLogicController implements BusinessApi {
    private final FeatureRepository featureRepository;

    @Override
    public BusinessDataDto getGreetings() {
        if (featureRepository.isActive(FANCY_GREETING)) {
            return new BusinessDataDto(FANCY_GREETING_TEXT);
        }
        return new BusinessDataDto(GREETING_TEXT);
    }

    @Override
    public BusinessDataDto getReports() {
        if (featureRepository.isActive(ELABORATED_REPORTS)) {
            return new BusinessDataDto(ELABORATED_REPORTS_TEXT);
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Not found"
        );
    }
}
