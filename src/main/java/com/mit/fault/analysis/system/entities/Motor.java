package com.mit.fault.analysis.system.entities;

public class Motor {


    float kvRating;
    float mvaRating;
    float zeroSequenceImpedance;
    float positiveSequenceImpedance;
    float negativeSequenceImpedance;
    float phaseAngle = 0;
    boolean isBase;

    public Motor(float kvRating, float mvaRating, float zeroSequenceImpedance, float positiveSequenceImpedance, float negativeSequenceImpedance, float phaseAngle, boolean isbase) {
        this.isBase = isbase;
        this.kvRating = kvRating;
        this.mvaRating = mvaRating;
        this.zeroSequenceImpedance = zeroSequenceImpedance;
        this.positiveSequenceImpedance = positiveSequenceImpedance;
        this.negativeSequenceImpedance = negativeSequenceImpedance;
        this.phaseAngle = phaseAngle;
    }

    public float getKvRating() {
        return kvRating;
    }

    public float getMvaRating() {
        return mvaRating;
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
