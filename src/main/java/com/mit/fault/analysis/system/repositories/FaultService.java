package com.mit.fault.analysis.system.repositories;

import com.mit.fault.analysis.system.entities.Generator;
import org.springframework.stereotype.Service;

@Service
public class FaultService {

    public double faultParameter(String faultType, float faultImpedance, Generator generator) {
//        LineToGround,LineToLine,LineToLineToGround

        double faultCurrent = 0;
        switch (faultType) {
            case "LineToGround":
                faultCurrent = lineToGround(generator.getEmf(), generator.getZeroSequenceImpedance(),
                        generator.getPositiveSequenceImpedance(), generator.getNegativeSequenceImpedance(), faultImpedance);
                break;
            case "LineToLine":
                faultCurrent = lineToLine(generator.getEmf(),
                        generator.getPositiveSequenceImpedance(), generator.getNegativeSequenceImpedance(), faultImpedance);
                break;
            case "LineToLineToGround":
                faultCurrent = lineToLineToGround(generator.getEmf(),
                        generator.getPositiveSequenceImpedance(), generator.getNegativeSequenceImpedance(),
                        generator.getZeroSequenceImpedance(), faultImpedance);
                break;
        }


        return faultCurrent;
    }

    public float lineToGround(float emf, float zeroSequenceImpedance, float positiveSequenceImpedance
            , float negativeSequenceImpedance, float faultImpedance) {
        float faultCurrent = 0;
        emf = (float) (emf / Math.sqrt(3));
        float equivalentImpedance = zeroSequenceImpedance + positiveSequenceImpedance + negativeSequenceImpedance + (3 * faultImpedance);
        faultCurrent = 3 * (emf / equivalentImpedance);

        return faultCurrent;
    }

    public double lineToLine(float emf, float positiveSequenceImpedance, float negativeSequenceImpedance, float faultImpedance) {
        double faultCurrent = 0;
        emf = (float) (emf / Math.sqrt(3));
        float equivalentImpedance = positiveSequenceImpedance + negativeSequenceImpedance + faultImpedance;
        faultCurrent = Math.sqrt(3) * (emf / equivalentImpedance);

        return faultCurrent;
    }


    public double lineToLineToGround(float emf, float positiveSequenceImpedance
            , float negativeSequenceImpedance, float zeroSequenceImpedance, float faultImpedance) {
        double faultCurrent = 0;
        emf = (float) (emf / Math.sqrt(3));
        float imp0 = ((zeroSequenceImpedance + (3 * faultImpedance)));
        float imp = (negativeSequenceImpedance * imp0);

        float imp1 = negativeSequenceImpedance + zeroSequenceImpedance + (3 * faultImpedance);

        float equivalentImpedance = positiveSequenceImpedance + (imp / imp1);

        float positiveCurrent = emf / equivalentImpedance;

        equivalentImpedance = negativeSequenceImpedance / imp1;

        faultCurrent = 3 * (positiveCurrent * equivalentImpedance);
        return faultCurrent;
    }


}
