package com.mit.fault.analysis.system.entities;

public class Motor {


    float emf;
    float zeroSequenceImpedance;
    float positiveSequenceImpedance;
    float negativeSequenceImpedance;
    float phaseAngle = 0;

    public Motor(float emf, float zeroSequenceImpedance, float positiveSequenceImpedance, float negativeSequenceImpedance, float phaseAngle) {
        this.emf = emf;
        this.zeroSequenceImpedance = zeroSequenceImpedance;
        this.positiveSequenceImpedance = positiveSequenceImpedance;
        this.negativeSequenceImpedance = negativeSequenceImpedance;
        this.phaseAngle = phaseAngle;
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

    public float getPhaseAngle() {
        return phaseAngle;
    }
}
