package com.mit.fault.analysis.system.entities;

public class TransmissionLine {


    float zeroSequenceImpedance;
    float positiveSequenceImpedance;
    float negativeSequenceImpedance;


    public TransmissionLine(float zeroSequenceImpedance, float positiveSequenceImpedance, float negativeSequenceImpedance) {

        this.zeroSequenceImpedance = zeroSequenceImpedance;
        this.positiveSequenceImpedance = positiveSequenceImpedance;
        this.negativeSequenceImpedance = negativeSequenceImpedance;

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


}
