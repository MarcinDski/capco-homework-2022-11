package com.capco.homework.featureflagmanager.controller;

import com.capco.homework.featureflagmanager.api.admin.model.Feature;
import com.capco.homework.featureflagmanager.api.admin.model.FeatureUpdateDto;
import com.capco.homework.featureflagmanager.api.business.model.BusinessDataDto;
import com.capco.homework.featureflagmanager.db.FeatureRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static com.capco.homework.featureflagmanager.api.business.model.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BusinessLogicControllerTest {
    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private AdminController adminController;

    @Autowired
    private BusinessLogicController businessLogicController;

    @AfterEach
    void cleanUp() {
        featureRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("FANCY_GREETING feature inactive should work")
    void test1() {
        // when:
        BusinessDataDto actual = businessLogicController.getGreetings();

        // then:
        assertThat(actual.getText()).isEqualTo(GREETING_TEXT);
    }

    @Test
    @DisplayName("FANCY_GREETING feature active should work")
    void test2() {
        // given:
        adminController.setFeature(Feature.FANCY_GREETING, new FeatureUpdateDto(true));

        // when:
        BusinessDataDto actual = businessLogicController.getGreetings();

        // then:
        assertThat(actual.getText()).isEqualTo(FANCY_GREETING_TEXT);
    }

    @Test
    @DisplayName("ELABORATED_REPORTS feature inactive should cause error 404 on access to reports endpoint")
    void test3() {
        // when:
        ResponseStatusException actual = assertThrows(ResponseStatusException.class, () -> businessLogicController.getReports());

        // then:
        assertThat(actual.getRawStatusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("FANCY_GREETING feature active should work")
    void test4() {
        // given:
        adminController.setFeature(Feature.ELABORATED_REPORTS, new FeatureUpdateDto(true));

        // when:
        BusinessDataDto actual = businessLogicController.getReports();

        // then:
        assertThat(actual.getText()).isEqualTo(ELABORATED_REPORTS_TEXT);
    }

}