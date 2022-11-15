package com.capco.homework.featureflagmanager.api.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureUpdateDto {
    private boolean featureActive;
}
