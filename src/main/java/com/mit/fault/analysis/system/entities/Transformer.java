package com.mit.fault.analysis.system.entities;

import com.mit.fault.analysis.system.DTO.ConnectionType;

public class Transformer {
    float emf;
    float zeroSequenceImpedance;
    float positiveSequenceImpedance;
    float negativeSequenceImpedance;

    ConnectionType connectionType;

    public Transformer(float emf, float zeroSequenceImpedance, float positiveSequenceImpedance, float negativeSequenceImpedance, ConnectionType connectionType) {
        this.emf = emf;
        this.zeroSequenceImpedance = zeroSequenceImpedance;
        this.positiveSequenceImpedance = positiveSequenceImpedance;
        this.negativeSequenceImpedance = negativeSequenceImpedance;
        this.connectionType=connectionType;
    }

    public float getEmf() {
        return emf;
    }

    public float getZeroSequenceImpedance() {
        return zeroSequenceImpedance;
    }

    public float getPositiveSequenceImpedance() {
        return positiveSequenceImpedance;
    }

    public float getNegativeSequenceImpedance() {
        return negativeSequenceImpedance;
    }

    public String connectionType() {
        return connectionType.toString();
    }
}
