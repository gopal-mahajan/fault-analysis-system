package com.mit.fault.analysis.system.services;

import com.mit.fault.analysis.system.DTO.PowerSystemType;
import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import com.mit.fault.analysis.system.entities.Transformer;
import com.mit.fault.analysis.system.entities.TransmissionLine;
import com.mit.fault.analysis.system.exceptions.BaseAlreadyChanged;
import com.mit.fault.analysis.system.repositories.PowerSystemDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PowerSystemDeviceService {

  @Autowired private PowerSystemDeviceRepository powerSystemDeviceRepository;

  private float baseKv = 0;
  private float baseMVA = 0;
  private PowerSystemDevice baseDevice;
  private int count = 0;

  public String addPowerSystemDevice(String name, PowerSystemDevice powerSystemDevice) {
    if (powerSystemDevice.isBase()) {
      baseDevice = powerSystemDevice;
      baseKv = powerSystemDevice.getKvRating();
      baseMVA = powerSystemDevice.getMvaRating();
    }
    return powerSystemDeviceRepository.addPowerSystemDevice(name, powerSystemDevice);
  }

  public String addTransformer(String name, Transformer transformer) {
    return powerSystemDeviceRepository.addTransformer(name, transformer);
  }

  public PowerSystemDevice getPowerSystemDevice(String name) {
    return powerSystemDeviceRepository.getPowerSystemDevice(name);
  }

  public void checkBase() throws BaseAlreadyChanged {
    try {
      if (count == 0) {
        count++;
        changeKVRating();
        for (Map.Entry<String, PowerSystemDevice> powerSystemDeviceEntry :
            powerSystemDeviceRepository.getPowerSystemDeviceMap().entrySet()) {
          changeBase(powerSystemDeviceEntry.getValue());
        }
        for (Map.Entry<String, Transformer> transformerEntry :
            powerSystemDeviceRepository.getTransformerMap().entrySet()) {
          changeBase(transformerEntry.getValue());
        }
      }
    } catch (Exception e) {
      throw new BaseAlreadyChanged();
    }
  }

  public void changeKVRating() {
    if (baseDevice.getPowerSystemType().equals(PowerSystemType.GENERATOR)) {
      Transformer transformer1 = powerSystemDeviceRepository.getTransformerMap().get("T1");
      transformer1.setNewSecondaryKVRating(
          (baseKv * transformer1.getSecondaryKVRating()) / transformer1.getKvRating());
      transformer1.setNewKVRating(baseKv);
      powerSystemDeviceRepository.editTransformerMap("T1", transformer1);

      TransmissionLine transmissionLine = powerSystemDeviceRepository.getTransmissionLine("TL");
      double factor = baseMVA / (Math.pow(transformer1.getNewSecondaryKVRating(), 2));
      transmissionLine.setZeroSequenceImpedance(
          transmissionLine.getZeroSequenceImpedance() * (float) factor);
      transmissionLine.setNegativeSequenceImpedance(
          transmissionLine.getNegativeSequenceImpedance() * (float) factor);
      transmissionLine.setPositiveSequenceImpedance(
          transmissionLine.getPositiveSequenceImpedance() * (float) factor);

      Transformer transformer = powerSystemDeviceRepository.getTransformerMap().get("T2");
      transformer.setNewSecondaryKVRating(
          (transformer1.getNewSecondaryKVRating() * transformer.getSecondaryKVRating())
              / transformer.getKvRating());
      transformer.setNewKVRating(transformer1.getNewSecondaryKVRating());
      powerSystemDeviceRepository.editTransformerMap("T2", transformer);

      powerSystemDeviceRepository
          .getPowerSystemDevice("M1")
          .setNewKVRating(transformer.getNewSecondaryKVRating());

    } else if (baseDevice.getPowerSystemType().equals(PowerSystemType.MOTOR)) {
      Transformer transformer1 = powerSystemDeviceRepository.getTransformerMap().get("T2");
      transformer1.setNewKVRating(
          (baseKv * transformer1.getKvRating()) / transformer1.getSecondaryKVRating());
      transformer1.setNewSecondaryKVRating(baseKv);
      powerSystemDeviceRepository.editTransformerMap("T2", transformer1);

      //            PowerSystemDevice powerSystemDevice =
      // powerSystemDeviceRepository.getPowerSystemDeviceMap().get("TL");
      //            powerSystemDevice.setNewKVRating(transformer.getNewKVRating());
      //            powerSystemDeviceRepository.editPowerSystemMap("TL", powerSystemDevice);

      TransmissionLine transmissionLine = powerSystemDeviceRepository.getTransmissionLine("TL");
      double factor = baseMVA / (Math.pow(transformer1.getNewKVRating(), 2));
      transmissionLine.setZeroSequenceImpedance(
          transmissionLine.getZeroSequenceImpedance() * (float) factor);
      transmissionLine.setNegativeSequenceImpedance(
          transmissionLine.getNegativeSequenceImpedance() * (float) factor);
      transmissionLine.setPositiveSequenceImpedance(
          transmissionLine.getPositiveSequenceImpedance() * (float) factor);

      Transformer transformer = powerSystemDeviceRepository.getTransformerMap().get("T1");
      transformer.setNewKVRating(
          (transformer1.getNewKVRating() * transformer.getKvRating())
              / transformer.getSecondaryKVRating());
      transformer.setNewSecondaryKVRating(transformer1.getNewKVRating());
      powerSystemDeviceRepository.editTransformerMap("T1", transformer);

      powerSystemDeviceRepository
          .getPowerSystemDevice("G1")
          .setNewKVRating(transformer.getNewKVRating());
    }
  }

  public float getBaseKv() {
    return baseKv;
  }

  public float getBaseMVA() {
    return baseMVA;
  }

  public void changeBase(PowerSystemDevice powerSystemDevice) {
    float temp =
        (float) Math.pow((powerSystemDevice.getKvRating() / powerSystemDevice.getNewKVRating()), 2)
            * (baseMVA / powerSystemDevice.getMvaRating());
    powerSystemDevice.setPositiveSequenceImpedance(
        powerSystemDevice.getPositiveSequenceImpedance() * temp);
    powerSystemDevice.setNegativeSequenceImpedance(
        powerSystemDevice.getNegativeSequenceImpedance() * temp);
    powerSystemDevice.setZeroSequenceImpedance(powerSystemDevice.getZeroSequenceImpedance() * temp);
  }

  public Map<String, Transformer> getTransformerMap() {
    return powerSystemDeviceRepository.getTransformerMap();
  }

  public Map<String, PowerSystemDevice> getPowerSystemDeviceMap() {
    return powerSystemDeviceRepository.getPowerSystemDeviceMap();
  }

  public String addTransmissionLine(String name, TransmissionLine transmissionLine) {
    return powerSystemDeviceRepository.addTransmissionLine(name, transmissionLine);
  }

  public TransmissionLine getTransmissionLine(String name) {
    return powerSystemDeviceRepository.getTransmissionLine(name);
  }

  //    public void changeBase(Transformer transformer) {
  //        float temp = (float) Math.pow((transformer.getKvRating() /
  // transformer.getNewKVRating()), 2) * (baseMVA / transformer.getMvaRating());
  //        transformer.setPositiveSequenceImpedance(transformer.getPositiveSequenceImpedance() *
  // temp);
  //        transformer.setNegativeSequenceImpedance(transformer.getNegativeSequenceImpedance() *
  // temp);
  //        transformer.setZeroSequenceImpedance(transformer.getZeroSequenceImpedance() * temp);
  //    }

}
