package com.mit.fault.analysis.system.entities;

import com.mit.fault.analysis.system.DTO.PowerSystem;

public class PowerSystemDevice {


    float kvRating;
    float mvaRating;
    float zeroSequenceImpedance;
    float positiveSequenceImpedance;
    float negativeSequenceImpedance;
    float phaseAngle = 0;
    boolean isBase = false;
    PowerSystem powerSystem;

    public PowerSystemDevice(float mvaRating, float kvRating, float zeroSequenceImpedance, float positiveSequenceImpedance,
                             float negativeSequenceImpedance, float phaseAngle, boolean isBase, PowerSystem powerSystem) {
        this.isBase = isBase;
        this.kvRating = kvRating;
        this.powerSystem = powerSystem;
        this.mvaRating = mvaRating;
        this.zeroSequenceImpedance = zeroSequenceImpedance;
        this.positiveSequenceImpedance = positiveSequenceImpedance;
        this.negativeSequenceImpedance = negativeSequenceImpedance;
        this.phaseAngle = phaseAngle;
    }

    public boolean isBase() {
        return isBase;
    }

    public PowerSystem getPowerSystem() {
        return powerSystem;
    }

    public float getMvaRating() {
        return mvaRating;
    }

    public float getKvRating() {
        return kvRating;
    }

    public float getZeroSequenceImpedance() {
        return zeroSequenceImpedance;
    }

    public void setZeroSequenceImpedance(float zeroSequenceImpedance) {
        this.zeroSequenceImpedance = zeroSequenceImpedance;
    }

    public float getPositiveSequenceImpedance() {
        return positiveSequenceImpedance;
    }

    public void setPositiveSequenceImpedance(float positiveSequenceImpedance) {
        this.positiveSequenceImpedance = positiveSequenceImpedance;
    }

    public float getNegativeSequenceImpedance() {
        return negativeSequenceImpedance;
    }

    public void setNegativeSequenceImpedance(float negativeSequenceImpedance) {
        this.negativeSequenceImpedance = negativeSequenceImpedance;
    }

    public float getPhaseAngle() {
        return phaseAngle;
    }
}
