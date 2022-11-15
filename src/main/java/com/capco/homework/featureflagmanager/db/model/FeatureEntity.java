package com.capco.homework.featureflagmanager.db.model;

import com.capco.homework.featureflagmanager.api.admin.model.Feature;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FEATURE")
public class FeatureEntity {
    @Id
    @Enumerated(EnumType.STRING)
    private Feature featureId;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean featureActive;
}
