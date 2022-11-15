package com.capco.homework.featureflagmanager.db;

import com.capco.homework.featureflagmanager.api.admin.model.Feature;
import com.capco.homework.featureflagmanager.db.model.FeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeatureRepository extends JpaRepository<FeatureEntity, Feature> {

    default boolean isActive(Feature featureId) {
        Optional<FeatureEntity> entity = findById(featureId);
        if (entity.isPresent()) {
            return entity.get().isFeatureActive();
        }
        else {
            return false;
        }
    }

}
