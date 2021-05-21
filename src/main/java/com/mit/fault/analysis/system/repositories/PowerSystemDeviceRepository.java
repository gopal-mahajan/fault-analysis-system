package com.mit.fault.analysis.system.repositories;

import com.mit.fault.analysis.system.DTO.PowerSystemType;
import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import com.mit.fault.analysis.system.entities.Transformer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PowerSystemDeviceRepository {

    private final Map<String, PowerSystemDevice> powerSystemDeviceMap = new HashMap<>();
    private final Map<String, Transformer> transformerMap = new HashMap<>();
    private float baseKv = 0;
    private float baseMVA = 0;
    private PowerSystemDevice baseDevice;

    public PowerSystemDevice getPowerSystemDevice(String powerSystemDeviceName) {
        if(transformerMap.containsKey(powerSystemDeviceName))
            return transformerMap.get(powerSystemDeviceName);
        return powerSystemDeviceMap.get(powerSystemDeviceName);
    }

    public String addPowerSystemDevice(String powerSystemDeviceName, PowerSystemDevice powerSystemDevice) {
        if (powerSystemDevice.isBase()) {
            baseDevice = powerSystemDevice;
            baseKv = powerSystemDevice.getKvRating();
            baseMVA = powerSystemDevice.getMvaRating();
        }

        powerSystemDeviceMap.put(powerSystemDeviceName, powerSystemDevice);
        return powerSystemDevice.getPowerSystemType() + " Added Successfully.";
    }

    public String addTransformer(String powerSystemDeviceName, Transformer transformer) {
        transformerMap.put(powerSystemDeviceName, transformer);
        return transformer.getPowerSystemType() + " Added Successfully.";
    }

    public void checkBase() {
        changeKVRating();
        for (Map.Entry<String, PowerSystemDevice> powerSystemDeviceEntry : powerSystemDeviceMap.entrySet()) {
            changeBase(powerSystemDeviceEntry.getValue());
        }
        for(Map.Entry<String,Transformer> transformerEntry:transformerMap.entrySet()){
            changeBase(transformerEntry.getValue());
        }
        return ;
    }

    public void changeKVRating() {
        if (baseDevice.getPowerSystemType().equals(PowerSystemType.GENERATOR)) {
            Transformer transformer = transformerMap.get("T1");
            transformer.setNewSecondaryKVRating((baseKv * transformer.getSecondaryKVRating()) / transformer.getKvRating());
            transformer.setNewKVRating(baseKv);
            transformerMap.put("T1", transformer);

            PowerSystemDevice powerSystemDevice = powerSystemDeviceMap.get("TL");
            powerSystemDevice.setNewKVRating(transformer.getSecondaryKVRating());
            powerSystemDeviceMap.put("TL", powerSystemDevice);

            transformer = transformerMap.get("T2");
            transformer.setNewSecondaryKVRating((powerSystemDevice.getKvRating() * transformer.getSecondaryKVRating()) / transformer.getKvRating());
            transformer.setNewKVRating(powerSystemDevice.getKvRating());
            transformerMap.put("T2", transformer);

            powerSystemDeviceMap.get("M1").setNewKVRating(transformer.getSecondaryKVRating());

        } else {
            Transformer transformer = transformerMap.get("T2");
            transformer.setNewKVRating((baseKv * transformer.getKvRating()) / transformer.getSecondaryKVRating());
            transformer.setNewSecondaryKVRating(baseKv);
            transformerMap.put("T2", transformer);

            PowerSystemDevice powerSystemDevice = powerSystemDeviceMap.get("TL");
            powerSystemDevice.setNewKVRating(transformer.getKvRating());
            powerSystemDeviceMap.put("TL", powerSystemDevice);

            transformer = transformerMap.get("T1");
            transformer.setNewKVRating((powerSystemDevice.getKvRating() * transformer.getKvRating()) / transformer.getSecondaryKVRating());
            transformer.setNewSecondaryKVRating(powerSystemDevice.getKvRating());
            transformerMap.put("T2", transformer);
            powerSystemDeviceMap.get("G1").setNewKVRating(transformer.getKvRating());
         }
    }


    public void changeBase(PowerSystemDevice powerSystemDevice) {
        float temp = (float) Math.pow((powerSystemDevice.getKvRating() / powerSystemDevice.getNewKVRating()), 2) * (baseMVA / powerSystemDevice.getMvaRating());
        powerSystemDevice.setPositiveSequenceImpedance(powerSystemDevice.getPositiveSequenceImpedance() * temp);
        powerSystemDevice.setNegativeSequenceImpedance(powerSystemDevice.getNegativeSequenceImpedance() * temp);
        powerSystemDevice.setZeroSequenceImpedance(powerSystemDevice.getZeroSequenceImpedance() * temp);
    }


    public void changeBase(Transformer transformer) {
        float temp = (float) Math.pow((transformer.getKvRating() / transformer.getNewKVRating()), 2) * (baseMVA / transformer.getMvaRating());
        transformer.setPositiveSequenceImpedance(transformer.getPositiveSequenceImpedance() * temp);
        transformer.setNegativeSequenceImpedance(transformer.getNegativeSequenceImpedance() * temp);
        transformer.setZeroSequenceImpedance(transformer.getZeroSequenceImpedance() * temp);
    }


}
