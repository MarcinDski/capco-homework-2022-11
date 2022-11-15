package com.capco.homework.featureflagmanager.controller;

import com.capco.homework.featureflagmanager.api.admin.model.Feature;
import com.capco.homework.featureflagmanager.api.admin.model.FeatureDto;
import com.capco.homework.featureflagmanager.api.admin.model.FeatureUpdateDto;
import com.capco.homework.featureflagmanager.db.FeatureRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AdminControllerTest {
    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private AdminController adminController;

    @AfterEach
    void cleanUp() {
        featureRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("AdminController::getAllFeatures actually returns all available features")
    void test1() {
        // when:
        List<FeatureDto> returned = adminController.getAllFeatures();

        // then:
        assertThat(returned).isNotNull();
        assertThat(returned.size()).isEqualTo(Feature.values().length);
        Set<Feature> returnedSet = returned.stream().map(FeatureDto::getFeatureId).collect(Collectors.toSet());
        for (Feature feature : Feature.values()) {
            assertThat(returnedSet).contains(feature);
        }
    }

    @Test
    @DisplayName("AdminController::setFeature turns on a feature")
    void test2() {
        // given:
        Feature feature = Feature.values()[0];

        // when:
        adminController.setFeature(feature, new FeatureUpdateDto(true));

        // then:
        Map<Feature, FeatureDto> returned = adminController.getAllFeatures().stream()
                .collect(Collectors.toMap(FeatureDto::getFeatureId, Function.identity()));
        assertThat(returned.get(feature).isFeatureActive()).isTrue();
    }

    @Test
    @DisplayName("AdminController::setFeature turns off a feature")
    void test3() {
        // given:
        Feature feature = Feature.values()[0];

        // when:
        adminController.setFeature(feature, new FeatureUpdateDto(true));
        adminController.setFeature(feature, new FeatureUpdateDto(false));

        // then:
        Map<Feature, FeatureDto> returned = adminController.getAllFeatures().stream()
                .collect(Collectors.toMap(FeatureDto::getFeatureId, Function.identity()));
        assertThat(returned.get(feature).isFeatureActive()).isFalse();
    }

    @Test
    @DisplayName("AdminController::setFeature turning off already disabled feature doesn't cause errors")
    void test4() {
        // given:
        Feature feature = Feature.values()[0];

        // when:
        adminController.setFeature(feature, new FeatureUpdateDto(false));

        // then:
        Map<Feature, FeatureDto> returned = adminController.getAllFeatures().stream()
                .collect(Collectors.toMap(FeatureDto::getFeatureId, Function.identity()));
        assertThat(returned.get(feature).isFeatureActive()).isFalse();
    }

    @Test
    @DisplayName("AdminController::setFeature turning on a feature twice doesn't cause errors")
    void test5() {
        // given:
        Feature feature = Feature.values()[0];

        // when:
        adminController.setFeature(feature, new FeatureUpdateDto(true));
        adminController.setFeature(feature, new FeatureUpdateDto(true));

        // then:
        Map<Feature, FeatureDto> returned = adminController.getAllFeatures().stream()
                .collect(Collectors.toMap(FeatureDto::getFeatureId, Function.identity()));
        assertThat(returned.get(feature).isFeatureActive()).isTrue();
    }
}