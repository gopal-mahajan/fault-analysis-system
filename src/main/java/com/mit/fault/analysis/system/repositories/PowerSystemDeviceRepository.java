package com.mit.fault.analysis.system.repositories;

import com.mit.fault.analysis.system.DTO.PowerSystem;
import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PowerSystemDeviceRepository {
    private final Map<String, PowerSystemDevice> generatorMap = new HashMap<>();
    private final Map<String, PowerSystemDevice> motorMap = new HashMap<>();
    private final List<String> list = new ArrayList<>();
    private float baseKv = 0;
    private float baseMVA = 0;
    private PowerSystemDevice baseDevice;

    public PowerSystemDevice getPowerSystemDevice(String powerSystemDeviceName) {
        if (generatorMap.containsKey(powerSystemDeviceName))
            return generatorMap.get(powerSystemDeviceName);
        else
            return motorMap.get(powerSystemDeviceName);
    }

    public String addPowerSystemDevice(String powerSystemDeviceName, PowerSystemDevice powerSystemDevice) {
        if (powerSystemDevice.isBase()) {
            baseDevice = powerSystemDevice;
            baseKv = powerSystemDevice.getKvRating();
            baseMVA = powerSystemDevice.getMvaRating();
        }

        if (PowerSystem.GENERATOR.equals(powerSystemDevice.getPowerSystem())) {
            generatorMap.put(powerSystemDeviceName, powerSystemDevice);
            return "Generator Added Successfully";
        } else {
            motorMap.put(powerSystemDeviceName, powerSystemDevice);
            return "Motor Added Successfully";
        }
    }

    public String checkBase() {
        String ans = "Changed base of: ";
        for (Map.Entry<String, PowerSystemDevice> powerSystemDeviceEntry : generatorMap.entrySet()) {
            if (powerSystemDeviceEntry.getValue() == baseDevice)
                continue;
            else {
                changeBase(powerSystemDeviceEntry.getValue());
                list.add(powerSystemDeviceEntry.getKey());
            }
        }
        ans = ans + " Generators: ";
        for (String name : list) {
            ans = ans + name;
        }

        list.removeAll(list);
        for (Map.Entry<String, PowerSystemDevice> powerSystemDeviceEntry : motorMap.entrySet()) {
            if (powerSystemDeviceEntry.getValue() == baseDevice)
                continue;
            else {
                changeBase(powerSystemDeviceEntry.getValue());
                list.add(powerSystemDeviceEntry.getKey());
            }
        }
        ans = ans + " Motors: ";
        for (String name : list) {
            ans = ans + name + " ";
        }

        return ans;
    }

    public void changeBase(PowerSystemDevice powerSystemDevice) {
        float temp = (float) Math.sqrt(powerSystemDevice.getKvRating() / baseKv) * (baseMVA / powerSystemDevice.getMvaRating());
        powerSystemDevice.setPositiveSequenceImpedance(powerSystemDevice.getPositiveSequenceImpedance() * temp);
        powerSystemDevice.setNegativeSequenceImpedance(powerSystemDevice.getNegativeSequenceImpedance() * temp);
        powerSystemDevice.setZeroSequenceImpedance(powerSystemDevice.getZeroSequenceImpedance() * temp);
    }

}
