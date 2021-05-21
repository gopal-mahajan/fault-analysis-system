package com.mit.fault.analysis.system.services;

import com.mit.fault.analysis.system.DTO.ConnectionType;
import com.mit.fault.analysis.system.DTO.FaultType;
import com.mit.fault.analysis.system.DTO.PositionOfFault;
import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import com.mit.fault.analysis.system.entities.Transformer;
import com.mit.fault.analysis.system.repositories.FaultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FaultService {
    @Autowired
    FaultRepository faultRepository;

    private final Map<String, PowerSystemDevice> powerSystemDeviceMap = faultRepository.getPowerSystemDeviceMap();
    private final Map<String, Transformer> transformerMap = faultRepository.getTransformerMap();


    public double getFaultParameters(PositionOfFault positionOfFault, FaultType faultType, float faultImpedance) {

        float equivalentPositiveSequenceImpedance = getPositiveEquivalent(positionOfFault);
        float equivalentNegativeSequenceImpedance = getNegativeEquivalent(positionOfFault);
        float equivalentZeroSequenceImpedance = getZeroEquivalent(positionOfFault);

        double faultCurrent = 0;
        switch (faultType.toString()) {
            case "LINE_TO_GROUND":
                faultCurrent = lineToGround(1, equivalentZeroSequenceImpedance,
                        equivalentPositiveSequenceImpedance, equivalentNegativeSequenceImpedance, faultImpedance);
                break;
            case  "LINE_TO_LINE":
                faultCurrent = lineToLine(1, equivalentPositiveSequenceImpedance,
                        equivalentNegativeSequenceImpedance, faultImpedance);
                break;
            case "LINE_TO_LINE_GROUND":
                faultCurrent = lineToLineToGround(1, equivalentPositiveSequenceImpedance, equivalentNegativeSequenceImpedance,
                        equivalentZeroSequenceImpedance, faultImpedance);
                break;
        }
        return faultCurrent;
    }
     public float lineToGround(float emf, float zeroSequenceImpedance, float positiveSequenceImpedance
            , float negativeSequenceImpedance, float faultImpedance) {
        float faultCurrent = 0;
//        emf = (float) (emf / Math.sqrt(3));
        float equivalentImpedance = zeroSequenceImpedance + positiveSequenceImpedance + negativeSequenceImpedance + (3 * faultImpedance);
        faultCurrent = 3 * (emf / equivalentImpedance);

        return faultCurrent;
    }

    public double lineToLine(float emf, float positiveSequenceImpedance, float negativeSequenceImpedance, float faultImpedance) {
        double faultCurrent = 0;
//        emf = (float) (emf / Math.sqrt(3));
        float equivalentImpedance = positiveSequenceImpedance + negativeSequenceImpedance + faultImpedance;
        faultCurrent = Math.sqrt(3) * (emf / equivalentImpedance);
        return faultCurrent;
    }


    public double lineToLineToGround(float emf, float positiveSequenceImpedance
            , float negativeSequenceImpedance, float zeroSequenceImpedance, float faultImpedance) {
        double faultCurrent = 0;
//        emf = (float) (emf / Math.sqrt(3));
        float imp0 = ((zeroSequenceImpedance + (3 * faultImpedance)));
        float imp = (negativeSequenceImpedance * imp0);
        float imp1 = negativeSequenceImpedance + zeroSequenceImpedance + (3 * faultImpedance);
        float equivalentImpedance = positiveSequenceImpedance + (imp / imp1);
        float positiveCurrent = emf / equivalentImpedance;
        equivalentImpedance = negativeSequenceImpedance / imp1;
        faultCurrent = 3 * (positiveCurrent * equivalentImpedance);
        return faultCurrent;
    }


    private float getZeroEquivalent(PositionOfFault positionOfFault) {
        ConnectionType connectionTypeOfT1 = transformerMap.get("T1").getConnectionType();
        ConnectionType connectionTypeOfT2 = transformerMap.get("T2").getConnectionType();

        if (ConnectionType.STAR_DELTA.equals(connectionTypeOfT1) && ConnectionType.STAR_DELTA.equals(connectionTypeOfT2)) {
            return powerSystemDeviceMap.get("G1").getZeroSequenceImpedance() + transformerMap.get("T1").getZeroSequenceImpedance();
        } else if (ConnectionType.STAR_DELTA.equals(connectionTypeOfT1) && ConnectionType.DELTA_DELTA.equals(connectionTypeOfT2)) {
            float temp = powerSystemDeviceMap.get("G1").getZeroSequenceImpedance() + transformerMap.get("T1").getZeroSequenceImpedance();
            return ((temp * transformerMap.get("T1").getZeroSequenceImpedance()) / (temp + transformerMap.get("T1").getZeroSequenceImpedance()));
        } else if (ConnectionType.STAR_DELTA.equals(connectionTypeOfT1) && ConnectionType.DELTA_STAR.equals(connectionTypeOfT2)) {
            float temp = powerSystemDeviceMap.get("G1").getZeroSequenceImpedance() + transformerMap.get("T1").getZeroSequenceImpedance();
            float temp1 = powerSystemDeviceMap.get("M1").getZeroSequenceImpedance() + transformerMap.get("T2").getZeroSequenceImpedance();
            return ((temp * temp1) / (temp + temp1));
        } else if (ConnectionType.DELTA_DELTA.equals(connectionTypeOfT1) && ConnectionType.STAR_DELTA.equals(connectionTypeOfT2)) {
            return transformerMap.get("T1").getZeroSequenceImpedance();
        } else if (ConnectionType.DELTA_DELTA.equals(connectionTypeOfT1) && ConnectionType.DELTA_DELTA.equals(connectionTypeOfT2)) {
            float temp = transformerMap.get("T1").getZeroSequenceImpedance() * transformerMap.get("T2").getZeroSequenceImpedance();
            float temp1 = transformerMap.get("T1").getZeroSequenceImpedance() + transformerMap.get("T2").getZeroSequenceImpedance();
            return temp / temp1;
        } else if (ConnectionType.DELTA_DELTA.equals(connectionTypeOfT1) && ConnectionType.DELTA_STAR.equals(connectionTypeOfT2)) {
            float temp = transformerMap.get("T1").getZeroSequenceImpedance();
            float temp1 = powerSystemDeviceMap.get("M1").getZeroSequenceImpedance() + transformerMap.get("T2").getZeroSequenceImpedance();
            return ((temp * temp1) / (temp + temp1));
        } else if (ConnectionType.DELTA_STAR.equals(connectionTypeOfT1) && ConnectionType.DELTA_DELTA.equals(connectionTypeOfT2)) {
            return transformerMap.get("T2").getZeroSequenceImpedance();
        } else if (ConnectionType.DELTA_STAR.equals(connectionTypeOfT1) && ConnectionType.DELTA_STAR.equals(connectionTypeOfT2)) {
            return transformerMap.get("T2").getZeroSequenceImpedance() + powerSystemDeviceMap.get("M1").getZeroSequenceImpedance();
        } else if (ConnectionType.DELTA_STAR.equals(connectionTypeOfT1) && ConnectionType.STAR_DELTA.equals(connectionTypeOfT2)) {
            if (PositionOfFault.MID_OF_LINE.equals(positionOfFault)) {
                float temp = transformerMap.get("T1").getZeroSequenceImpedance() + (powerSystemDeviceMap.get("M1").getZeroSequenceImpedance() / 2);
                float temp1 = (powerSystemDeviceMap.get("M1").getZeroSequenceImpedance() / 2) + transformerMap.get("T2").getZeroSequenceImpedance();
                return (temp * temp1 / (temp + temp1));
            } else {
                return transformerMap.get("T1").getZeroSequenceImpedance() + powerSystemDeviceMap.get("M1").getZeroSequenceImpedance()
                        + transformerMap.get("T2").getZeroSequenceImpedance();
            }
        }


        return 0;
    }

    private float getPositiveEquivalent(PositionOfFault positionOfFault) {
        float tempSum = 0;
        if (PositionOfFault.GENERATOR.equals(positionOfFault) || PositionOfFault.MOTOR.equals(positionOfFault)) {
            for (Map.Entry<String, PowerSystemDevice> powerSystemDeviceEntry : powerSystemDeviceMap.entrySet()) {
                tempSum = +powerSystemDeviceEntry.getValue().getPositiveSequenceImpedance();
            }
            for (Map.Entry<String, Transformer> transformerEntry : transformerMap.entrySet()) {
                tempSum += transformerEntry.getValue().getPositiveSequenceImpedance();
            }

        } else {
            float temp1 = powerSystemDeviceMap.get("G1").getPositiveSequenceImpedance() +
                    transformerMap.get("T1").getPositiveSequenceImpedance() + ((powerSystemDeviceMap.get("G1").getPositiveSequenceImpedance()) / 2);
            float temp2 = ((powerSystemDeviceMap.get("G1").getPositiveSequenceImpedance()) / 2) +
                    transformerMap.get("T1").getPositiveSequenceImpedance() + powerSystemDeviceMap.get("M1").getPositiveSequenceImpedance();
            tempSum = ((temp1 * temp2) / (temp1 + temp2));
        }
        return tempSum;
    }

    private float getNegativeEquivalent(PositionOfFault positionOfFault) {
        float tempSum = 0;
        if (PositionOfFault.GENERATOR.equals(positionOfFault) || PositionOfFault.MOTOR.equals(positionOfFault)) {
            for (Map.Entry<String, PowerSystemDevice> powerSystemDeviceEntry : powerSystemDeviceMap.entrySet()) {
                tempSum = +powerSystemDeviceEntry.getValue().getNegativeSequenceImpedance();
            }
            for (Map.Entry<String, Transformer> transformerEntry : transformerMap.entrySet()) {
                tempSum += transformerEntry.getValue().getNegativeSequenceImpedance();
            }

        } else {
            float temp1 = powerSystemDeviceMap.get("G1").getNegativeSequenceImpedance() +
                    transformerMap.get("T1").getNegativeSequenceImpedance() + ((powerSystemDeviceMap.get("G1").getNegativeSequenceImpedance()) / 2);
            float temp2 = ((powerSystemDeviceMap.get("G1").getNegativeSequenceImpedance()) / 2) +
                    transformerMap.get("T1").getNegativeSequenceImpedance() + powerSystemDeviceMap.get("M1").getNegativeSequenceImpedance();
            tempSum = ((temp1 * temp2) / (temp1 + temp2));
        }
        return tempSum;
    }



}
