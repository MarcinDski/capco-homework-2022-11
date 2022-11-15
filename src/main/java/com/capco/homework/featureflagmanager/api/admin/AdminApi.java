package com.capco.homework.featureflagmanager.api.admin;

import com.capco.homework.featureflagmanager.api.admin.model.Feature;
import com.capco.homework.featureflagmanager.api.admin.model.FeatureDto;
import com.capco.homework.featureflagmanager.api.admin.model.FeatureUpdateDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// we might want to move feature flag administration to a separate microservice to make it more versatile.
public interface AdminApi {
    @ApiOperation(value = "Returns status of all feature flags.")
    @GetMapping("/api/admin/features")
    List<FeatureDto> getAllFeatures();

    @ApiOperation(value = "Sets a feature flag.")
    @PutMapping("/api/admin/features/{featureId}")
    void setFeature(@PathVariable("featureId") Feature featureId, @RequestBody FeatureUpdateDto updateDto);
}
