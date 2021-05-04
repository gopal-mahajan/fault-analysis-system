package com.mit.fault.analysis.system.repositories;

import com.mit.fault.analysis.system.DTO.PowerSystem;
import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PowerSystemDeviceRepository {
    private final Map<String, PowerSystemDevice> generatorMap = new HashMap<>();
    private final Map<String, PowerSystemDevice> motorMap = new HashMap<>();
    private float baseKv = 0;
    private float baseMVA = 0;

    public PowerSystemDevice getPowerSystemDevice(String powerSystemDeviceName) {
        return generatorMap.get(powerSystemDeviceName);
    }

    public String addPowerSystemDevice(String powerSystemDeviceName, PowerSystemDevice powerSystem) {
        if (powerSystem.isBase()) {
            baseKv = powerSystem.getKvRating();
            baseMVA = powerSystem.getMvaRating();
        }

        if (PowerSystem.GENERATOR.equals(powerSystem.getPowerSystem())) {
            generatorMap.put(powerSystemDeviceName, powerSystem);
            return "Generator Added Successfully";

        } else {
            motorMap.put(powerSystemDeviceName, powerSystem);
            return "Motor Added Successfully";
        }
    }

    public void checkBase() {

    }

}
