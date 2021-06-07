package com.mit.fault.analysis.system.services;

import com.mit.fault.analysis.system.DTO.ConnectionType;
import com.mit.fault.analysis.system.DTO.FaultType;
import com.mit.fault.analysis.system.DTO.PositionOfFault;
import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import com.mit.fault.analysis.system.entities.Transformer;
import com.mit.fault.analysis.system.entities.TransmissionLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FaultService {

  @Autowired PowerSystemDeviceService powerSystemDeviceService;

  public double getFaultParameters(
      PositionOfFault positionOfFault, FaultType faultType, float faultImpedance) {
    switch (faultType) {
      case LINE_TO_GROUND:
        System.out.println(
            "0: "
                + getZeroEquivalent(positionOfFault)
                + " +: "
                + getPositiveEquivalent(positionOfFault)
                + " -: "
                + getNegativeEquivalent(positionOfFault));
        return lineToGround(
            1,
            getZeroEquivalent(positionOfFault),
            getPositiveEquivalent(positionOfFault),
            getNegativeEquivalent(positionOfFault),
            faultImpedance);
      case LINE_TO_LINE:
        return lineToLine(
            1,
            getPositiveEquivalent(positionOfFault),
            getNegativeEquivalent(positionOfFault),
            faultImpedance);
      case LINE_TO_LINE_GROUND:
        return lineToLineToGround(
            1,
            getPositiveEquivalent(positionOfFault),
            getNegativeEquivalent(positionOfFault),
            getZeroEquivalent(positionOfFault),
            faultImpedance);
    }
    return 0;
  }

  public double getFinalCurrent(double faultCurrent) {

    return (faultCurrent * powerSystemDeviceService.getBaseMVA())
        / ((Math.sqrt(3) * powerSystemDeviceService.getBaseKv()));
  }

  public float lineToGround(
      float emf,
      float zeroSequenceImpedance,
      float positiveSequenceImpedance,
      float negativeSequenceImpedance,
      float faultImpedance) {

    float equivalentImpedance =
        zeroSequenceImpedance
            + positiveSequenceImpedance
            + negativeSequenceImpedance
            + (3 * faultImpedance);
    return 3 * (emf / equivalentImpedance);
  }

  public double lineToLine(
      float emf,
      float positiveSequenceImpedance,
      float negativeSequenceImpedance,
      float faultImpedance) {

    float equivalentImpedance =
        positiveSequenceImpedance + negativeSequenceImpedance + faultImpedance;
    return Math.sqrt(3) * (emf / equivalentImpedance);
  }

  public double lineToLineToGround(
      float emf,
      float positiveSequenceImpedance,
      float negativeSequenceImpedance,
      float zeroSequenceImpedance,
      float faultImpedance) {

    float imp = (negativeSequenceImpedance * ((zeroSequenceImpedance + (3 * faultImpedance))));
    float imp1 = negativeSequenceImpedance + zeroSequenceImpedance + (3 * faultImpedance);
    return 3
        * ((emf / (positiveSequenceImpedance + (imp / imp1))) * (negativeSequenceImpedance / imp1));
  }

  private Map<String, Transformer> getTransformerMap() {
    return powerSystemDeviceService.getTransformerMap();
  }

  private Map<String, PowerSystemDevice> getPowerSystemDeviceMap() {
    return powerSystemDeviceService.getPowerSystemDeviceMap();
  }

  private TransmissionLine getTransmissionLine() {
    return powerSystemDeviceService.getTransmissionLine("TL");
  }

  private float getZeroEquivalent(PositionOfFault positionOfFault) {
    Map<String, Transformer> transformerMap = getTransformerMap();
    Map<String, PowerSystemDevice> powerSystemDeviceMap = getPowerSystemDeviceMap();
    TransmissionLine transmissionLine = getTransmissionLine();
    ConnectionType connectionTypeOfT1 = transformerMap.get("T1").getConnectionType();
    ConnectionType connectionTypeOfT2 = transformerMap.get("T2").getConnectionType();
    float temp, temp1;
    if (ConnectionType.STAR_DELTA.equals(connectionTypeOfT1)
        && ConnectionType.STAR_DELTA.equals(connectionTypeOfT2)) {
      return powerSystemDeviceMap.get("G1").getZeroSequenceImpedance()
          + transformerMap.get("T1").getZeroSequenceImpedance();
    } else if (ConnectionType.STAR_DELTA.equals(connectionTypeOfT1)
        && ConnectionType.DELTA_DELTA.equals(connectionTypeOfT2)) {
      temp =
          powerSystemDeviceMap.get("G1").getZeroSequenceImpedance()
              + transformerMap.get("T1").getZeroSequenceImpedance();
      return ((temp * transformerMap.get("T1").getZeroSequenceImpedance())
          / (temp + transformerMap.get("T1").getZeroSequenceImpedance()));
    } else if (ConnectionType.STAR_DELTA.equals(connectionTypeOfT1)
        && ConnectionType.DELTA_STAR.equals(connectionTypeOfT2)) {
      temp =
          powerSystemDeviceMap.get("G1").getZeroSequenceImpedance()
              + transformerMap.get("T1").getZeroSequenceImpedance();
      temp1 =
          powerSystemDeviceMap.get("M1").getZeroSequenceImpedance()
              + transformerMap.get("T2").getZeroSequenceImpedance();
      return ((temp * temp1) / (temp + temp1));
    } else if (ConnectionType.DELTA_DELTA.equals(connectionTypeOfT1)
        && ConnectionType.STAR_DELTA.equals(connectionTypeOfT2)) {
      return transformerMap.get("T1").getZeroSequenceImpedance();
    } else if (ConnectionType.DELTA_DELTA.equals(connectionTypeOfT1)
        && ConnectionType.DELTA_DELTA.equals(connectionTypeOfT2)) {
      temp =
          transformerMap.get("T1").getZeroSequenceImpedance()
              * transformerMap.get("T2").getZeroSequenceImpedance();
      temp1 =
          transformerMap.get("T1").getZeroSequenceImpedance()
              + transformerMap.get("T2").getZeroSequenceImpedance();
      return temp / temp1;
    } else if (ConnectionType.DELTA_DELTA.equals(connectionTypeOfT1)
        && ConnectionType.DELTA_STAR.equals(connectionTypeOfT2)) {
      temp = transformerMap.get("T1").getZeroSequenceImpedance();
      temp1 =
          powerSystemDeviceMap.get("M1").getZeroSequenceImpedance()
              + transformerMap.get("T2").getZeroSequenceImpedance();
      return ((temp * temp1) / (temp + temp1));
    } else if (ConnectionType.DELTA_DELTA.equals(connectionTypeOfT1)
        && ConnectionType.DELTA_STAR.equals(connectionTypeOfT2)) {
      return transformerMap.get("T1").getZeroSequenceImpedance();
    } else if ((ConnectionType.DELTA_STAR.equals(connectionTypeOfT1)
            || (ConnectionType.STAR_STAR.equals(connectionTypeOfT1)))
        && ConnectionType.DELTA_DELTA.equals(connectionTypeOfT2)) {
      return transformerMap.get("T2").getZeroSequenceImpedance();
    } else if ((ConnectionType.DELTA_STAR.equals(connectionTypeOfT1)
            || (ConnectionType.STAR_STAR.equals(connectionTypeOfT1)))
        && ConnectionType.DELTA_STAR.equals(connectionTypeOfT2)) {
      return transformerMap.get("T2").getZeroSequenceImpedance()
          + powerSystemDeviceMap.get("M1").getZeroSequenceImpedance();
    } else if ((ConnectionType.DELTA_STAR.equals(connectionTypeOfT1)
        && ConnectionType.STAR_STAR.equals(connectionTypeOfT2))) {
      if (PositionOfFault.MID_OF_LINE.equals(positionOfFault)) {
        temp =
            transformerMap.get("T1").getZeroSequenceImpedance()
                + (transmissionLine.getZeroSequenceImpedance() / 2);
        temp1 =
            (transmissionLine.getZeroSequenceImpedance() / 2)
                + transformerMap.get("T2").getZeroSequenceImpedance()
                + powerSystemDeviceMap.get("M1").getZeroSequenceImpedance();
        return (temp * temp1) / (temp + temp1);
      } else {
        return transformerMap.get("T1").getZeroSequenceImpedance()
            + transmissionLine.getZeroSequenceImpedance()
            + transformerMap.get("T2").getZeroSequenceImpedance()
            + powerSystemDeviceMap.get("M1").getZeroSequenceImpedance();
      }
    } else if ((ConnectionType.STAR_STAR.equals(connectionTypeOfT1)
        && ConnectionType.STAR_STAR.equals(connectionTypeOfT2))) {
      if (PositionOfFault.MID_OF_LINE.equals(positionOfFault)) {
        temp =
            powerSystemDeviceMap.get("G1").getZeroSequenceImpedance()
                + transformerMap.get("T1").getZeroSequenceImpedance()
                + (transmissionLine.getZeroSequenceImpedance() / 2);
        temp1 =
            (transmissionLine.getZeroSequenceImpedance() / 2)
                + transformerMap.get("T2").getZeroSequenceImpedance()
                + powerSystemDeviceMap.get("M1").getZeroSequenceImpedance();
        return (temp * temp1) / (temp + temp1);
      } else {
        return transformerMap.get("T1").getZeroSequenceImpedance()
            + powerSystemDeviceMap.get("G1").getZeroSequenceImpedance()
            + transformerMap.get("T2").getZeroSequenceImpedance()
            + transmissionLine.getZeroSequenceImpedance()
            + powerSystemDeviceMap.get("M1").getZeroSequenceImpedance();
      }
    } else if (ConnectionType.STAR_STAR.equals(connectionTypeOfT1)
        && ConnectionType.STAR_DELTA.equals(connectionTypeOfT2)) {
      if (PositionOfFault.MID_OF_LINE.equals(positionOfFault)) {
        temp =
            powerSystemDeviceMap.get("G1").getZeroSequenceImpedance()
                + transformerMap.get("T1").getZeroSequenceImpedance()
                + (transmissionLine.getZeroSequenceImpedance() / 2);
        temp1 =
            (transmissionLine.getZeroSequenceImpedance() / 2)
                + transformerMap.get("T2").getZeroSequenceImpedance();
        return (temp * temp1) / (temp + temp1);
      } else {
        return powerSystemDeviceMap.get("G1").getZeroSequenceImpedance()
            + transformerMap.get("T1").getZeroSequenceImpedance()
            + transmissionLine.getZeroSequenceImpedance()
            + transformerMap.get("T2").getZeroSequenceImpedance();
      }
    } else if (ConnectionType.DELTA_STAR.equals(connectionTypeOfT1)
        && ConnectionType.STAR_DELTA.equals(connectionTypeOfT2)) {
      if (PositionOfFault.MID_OF_LINE.equals(positionOfFault)) {
        temp =
            transformerMap.get("T1").getZeroSequenceImpedance()
                + (transmissionLine.getZeroSequenceImpedance() / 2);
        temp1 =
            (transmissionLine.getZeroSequenceImpedance() / 2)
                + transformerMap.get("T2").getZeroSequenceImpedance();
        return (temp * temp1 / (temp + temp1));
      } else {
        return transformerMap.get("T1").getZeroSequenceImpedance()
            + transmissionLine.getZeroSequenceImpedance()
            + transformerMap.get("T2").getZeroSequenceImpedance();
      }
    }
    return 0;
  }

  private float getPositiveEquivalent(PositionOfFault positionOfFault) {
    Map<String, Transformer> transformerMap = getTransformerMap();
    TransmissionLine transmissionLine = getTransmissionLine();
    Map<String, PowerSystemDevice> powerSystemDeviceMap = getPowerSystemDeviceMap();
    float tempSum = 0;
    if (PositionOfFault.GENERATOR.equals(positionOfFault)
        || PositionOfFault.MOTOR.equals(positionOfFault)) {
      for (Map.Entry<String, PowerSystemDevice> powerSystemDeviceEntry :
          powerSystemDeviceMap.entrySet()) {
        tempSum += powerSystemDeviceEntry.getValue().getPositiveSequenceImpedance();
      }
      for (Map.Entry<String, Transformer> transformerEntry : transformerMap.entrySet()) {
        tempSum += transformerEntry.getValue().getPositiveSequenceImpedance();
      }

    } else {
      float temp1 =
          getSum(
              transmissionLine.getPositiveSequenceImpedance(),
              transformerMap.get("T1").getPositiveSequenceImpedance(),
              powerSystemDeviceMap.get("G1").getPositiveSequenceImpedance());

      float temp2 =
          getSum(
              transmissionLine.getPositiveSequenceImpedance(),
              transformerMap.get("T2").getPositiveSequenceImpedance(),
              powerSystemDeviceMap.get("M1").getPositiveSequenceImpedance());
      tempSum = ((temp1 * temp2) / (temp1 + temp2));
    }
    return tempSum;
  }

  private float getNegativeEquivalent(PositionOfFault positionOfFault) {
    Map<String, Transformer> transformerMap = getTransformerMap();
    TransmissionLine transmissionLine = getTransmissionLine();
    Map<String, PowerSystemDevice> powerSystemDeviceMap = getPowerSystemDeviceMap();
    if (PositionOfFault.GENERATOR.equals(positionOfFault)
        || PositionOfFault.MOTOR.equals(positionOfFault)) {
      float tempSum = 0;
      for (Map.Entry<String, PowerSystemDevice> powerSystemDeviceEntry :
          powerSystemDeviceMap.entrySet()) {
        tempSum += powerSystemDeviceEntry.getValue().getNegativeSequenceImpedance();
      }
      for (Map.Entry<String, Transformer> transformerEntry : transformerMap.entrySet()) {
        tempSum += transformerEntry.getValue().getNegativeSequenceImpedance();
      }
      return tempSum;
    } else {
      float temp1 =
          getSum(
              transmissionLine.getNegativeSequenceImpedance(),
              transformerMap.get("T1").getNegativeSequenceImpedance(),
              powerSystemDeviceMap.get("G1").getNegativeSequenceImpedance());
      float temp2 =
          getSum(
              transmissionLine.getNegativeSequenceImpedance(),
              transformerMap.get("T2").getNegativeSequenceImpedance(),
              powerSystemDeviceMap.get("M1").getNegativeSequenceImpedance());
      return ((temp1 * temp2) / (temp1 + temp2));
    }
  }

  private float getSum(
      float transmissionLineImpedance,
      float transformerNegativeImpedance,
      float motorNegativeImpedance) {
    return transformerNegativeImpedance
        + motorNegativeImpedance
        + ((transmissionLineImpedance) / 2);
  }
}
