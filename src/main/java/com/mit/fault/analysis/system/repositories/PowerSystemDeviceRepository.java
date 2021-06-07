package com.mit.fault.analysis.system.repositories;

import com.mit.fault.analysis.system.DTO.PowerSystemType;
import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import com.mit.fault.analysis.system.entities.Transformer;
import com.mit.fault.analysis.system.entities.TransmissionLine;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PowerSystemDeviceRepository {

  private final Map<String, PowerSystemDevice> powerSystemDeviceMap = new HashMap<>();
  private final Map<String, Transformer> transformerMap = new HashMap<>();
  private final Map<String, TransmissionLine> transmissionLineMap = new HashMap<>();

  public Map<String, PowerSystemDevice> getPowerSystemDeviceMap() {
    return powerSystemDeviceMap;
  }

  public Map<String, Transformer> getTransformerMap() {
    return transformerMap;
  }

  public PowerSystemDevice getPowerSystemDevice(String powerSystemDeviceName) {
    if (transformerMap.containsKey(powerSystemDeviceName))
      return transformerMap.get(powerSystemDeviceName);
    return powerSystemDeviceMap.get(powerSystemDeviceName);
  }

  public String addPowerSystemDevice(
      String powerSystemDeviceName, PowerSystemDevice powerSystemDevice) {
    powerSystemDeviceMap.put(powerSystemDeviceName, powerSystemDevice);
    return powerSystemDevice.getPowerSystemType() + " Added Successfully.";
  }

  public String addTransformer(String powerSystemDeviceName, Transformer transformer) {
    transformerMap.put(powerSystemDeviceName, transformer);
    return transformer.getPowerSystemType() + " Added Successfully.";
  }

  public void editTransformerMap(String transformerName, Transformer transformer) {
    transformerMap.remove(transformerName);
    transformerMap.put(transformerName, transformer);
  }

  public void editPowerSystemMap(String powerSystemName, PowerSystemDevice powerSystemDevice) {
    powerSystemDeviceMap.remove(powerSystemName);
    powerSystemDeviceMap.put(powerSystemName, powerSystemDevice);
  }

  public String addTransmissionLine(String name, TransmissionLine transmissionLine) {
    transmissionLineMap.put(name, transmissionLine);
    return PowerSystemType.TRANSMISSION_LINE + " Added Successfully";
  }

  public TransmissionLine getTransmissionLine(String name) {
    return transmissionLineMap.get(name);
  }
}
