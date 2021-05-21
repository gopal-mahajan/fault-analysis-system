package com.mit.fault.analysis.system.services;


import com.mit.fault.analysis.system.DTO.PowerSystemType;
import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import com.mit.fault.analysis.system.entities.Transformer;
import com.mit.fault.analysis.system.repositories.PowerSystemDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PowerSystemDeviceService {

    @Autowired
    PowerSystemDeviceRepository powerSystemDeviceRepository;

    private float baseKv = 0;
    private float baseMVA = 0;
    private PowerSystemDevice baseDevice;


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


    public void checkBase() {
        changeKVRating();
        for (Map.Entry<String, PowerSystemDevice> powerSystemDeviceEntry : powerSystemDeviceRepository.getPowerSystemDeviceMap().entrySet()) {
            changeBase(powerSystemDeviceEntry.getValue());
        }
        for (Map.Entry<String, Transformer> transformerEntry : powerSystemDeviceRepository.getTransformerMap().entrySet()) {
            changeBase(transformerEntry.getValue());
        }
        return;
    }

    public void changeKVRating() {
        if (baseDevice.getPowerSystemType().equals(PowerSystemType.GENERATOR)) {
            Transformer transformer = powerSystemDeviceRepository.getTransformerMap().get("T1");
            transformer.setNewSecondaryKVRating((baseKv * transformer.getSecondaryKVRating()) / transformer.getKvRating());
            transformer.setNewKVRating(baseKv);
            powerSystemDeviceRepository.editTransformerMap("T1", transformer);

            PowerSystemDevice powerSystemDevice = powerSystemDeviceRepository.getPowerSystemDeviceMap().get("TL");
            powerSystemDevice.setNewKVRating(transformer.getSecondaryKVRating());
            powerSystemDeviceRepository.editPowerSystemMap("TL", powerSystemDevice);

            transformer = powerSystemDeviceRepository.getTransformerMap().get("T2");
            transformer.setNewSecondaryKVRating((powerSystemDevice.getKvRating() * transformer.getSecondaryKVRating()) / transformer.getKvRating());
            transformer.setNewKVRating(powerSystemDevice.getKvRating());
            powerSystemDeviceRepository.editTransformerMap("T2", transformer);

            powerSystemDeviceRepository.getPowerSystemDevice("M1").setNewKVRating(transformer.getSecondaryKVRating());

        } else {
            Transformer transformer = powerSystemDeviceRepository.getTransformerMap().get("T2");
            transformer.setNewKVRating((baseKv * transformer.getKvRating()) / transformer.getSecondaryKVRating());
            transformer.setNewSecondaryKVRating(baseKv);
            powerSystemDeviceRepository.editTransformerMap("T2", transformer);

            PowerSystemDevice powerSystemDevice = powerSystemDeviceRepository.getPowerSystemDeviceMap().get("TL");
            powerSystemDevice.setNewKVRating(transformer.getKvRating());
            powerSystemDeviceRepository.editPowerSystemMap("TL", powerSystemDevice);

            transformer = powerSystemDeviceRepository.getTransformerMap().get("T1");
            transformer.setNewKVRating((powerSystemDevice.getKvRating() * transformer.getKvRating()) / transformer.getSecondaryKVRating());
            transformer.setNewSecondaryKVRating(powerSystemDevice.getKvRating());
            powerSystemDeviceRepository.editTransformerMap("T1", transformer);
            powerSystemDeviceRepository.getPowerSystemDevice("G1").setNewKVRating(transformer.getSecondaryKVRating());
        }
    }


    public void changeBase(PowerSystemDevice powerSystemDevice) {
        float temp = (float) Math.pow((powerSystemDevice.getKvRating() / powerSystemDevice.getNewKVRating()), 2) * (baseMVA / powerSystemDevice.getMvaRating());
        powerSystemDevice.setPositiveSequenceImpedance(powerSystemDevice.getPositiveSequenceImpedance() * temp);
        powerSystemDevice.setNegativeSequenceImpedance(powerSystemDevice.getNegativeSequenceImpedance() * temp);
        powerSystemDevice.setZeroSequenceImpedance(powerSystemDevice.getZeroSequenceImpedance() * temp);
    }


//    public void changeBase(Transformer transformer) {
//        float temp = (float) Math.pow((transformer.getKvRating() / transformer.getNewKVRating()), 2) * (baseMVA / transformer.getMvaRating());
//        transformer.setPositiveSequenceImpedance(transformer.getPositiveSequenceImpedance() * temp);
//        transformer.setNegativeSequenceImpedance(transformer.getNegativeSequenceImpedance() * temp);
//        transformer.setZeroSequenceImpedance(transformer.getZeroSequenceImpedance() * temp);
//    }

}
