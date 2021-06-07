package com.mit.fault.analysis.system.entities;

import com.mit.fault.analysis.system.DTO.ConnectionType;
import com.mit.fault.analysis.system.DTO.PowerSystemType;
import lombok.Data;

@Data
public class Transformer extends PowerSystemDevice{
    float secondaryKVRating;
    float newSecondaryKVRating;
    ConnectionType connectionType;


    public Transformer(float mvaRating, float primaryKVRating,float secondaryKVRating, float zeroSequenceImpedance,
                       float positiveSequenceImpedance, float negativeSequenceImpedance,float phaseAngle,
                       boolean isBase, PowerSystemType powerSystemType, ConnectionType connectionType) {

    super(
        mvaRating,
        primaryKVRating,
        zeroSequenceImpedance,
        positiveSequenceImpedance,
        negativeSequenceImpedance,
        phaseAngle,
        isBase,
        powerSystemType);
        this.secondaryKVRating = secondaryKVRating;
        this.connectionType = connectionType;
        this.newSecondaryKVRating=secondaryKVRating;
    }
}
