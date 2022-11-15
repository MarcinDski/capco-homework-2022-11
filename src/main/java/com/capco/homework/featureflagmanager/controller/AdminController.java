package com.capco.homework.featureflagmanager.controller;

import com.capco.homework.featureflagmanager.api.admin.AdminApi;
import com.capco.homework.featureflagmanager.api.admin.model.Feature;
import com.capco.homework.featureflagmanager.api.admin.model.FeatureDto;
import com.capco.homework.featureflagmanager.api.admin.model.FeatureUpdateDto;
import com.capco.homework.featureflagmanager.db.FeatureRepository;
import com.capco.homework.featureflagmanager.db.model.FeatureEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
public class AdminController implements AdminApi {
    private final FeatureRepository featureRepository;

    @Override
    public List<FeatureDto> getAllFeatures() {
        Map<Feature, FeatureDto> dbMap = featureRepository.findAll().stream()
                .collect(Collectors.toMap(FeatureEntity::getFeatureId, entity -> new FeatureDto(entity.getFeatureId(), entity.isFeatureActive())));

        return List.of(Feature.values()).stream()
                .map(f -> getFeatureDto(f, dbMap))
                .collect(Collectors.toList());
    }

    public void setFeature(Feature featureId, FeatureUpdateDto updateDto) {
        if (updateDto.isFeatureActive()) {
            FeatureEntity featureEntity = new FeatureEntity();
            featureEntity.setFeatureId(featureId);
            featureEntity.setFeatureActive(true);
            featureRepository.save(featureEntity);
        } else if (featureRepository.findById(featureId).isPresent()) {
            featureRepository.deleteById(featureId);
        }
    }

    private FeatureDto getFeatureDto(Feature featureId, Map<Feature, FeatureDto> dbMap) {
        if (dbMap.containsKey(featureId)) {
            return dbMap.get(featureId);
        }
        // normally I use Masptruct for mapping;
        // usually I also have a separate business model layer;
        // but here we want it simple
        return new FeatureDto(featureId, false);
    }
}
