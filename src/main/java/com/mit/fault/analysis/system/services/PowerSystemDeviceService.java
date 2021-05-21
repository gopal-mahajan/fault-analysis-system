package com.mit.fault.analysis.system.services;


import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import com.mit.fault.analysis.system.entities.Transformer;
import com.mit.fault.analysis.system.repositories.PowerSystemDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PowerSystemDeviceService {

    @Autowired
    PowerSystemDeviceRepository powerSystemDeviceRepository;

    public String addPowerSystemDevice(String name, PowerSystemDevice powerSystem) {
        return powerSystemDeviceRepository.addPowerSystemDevice(name, powerSystem);
    }
    public String addTransformer(String name, Transformer transformer) {
        return powerSystemDeviceRepository.addTransformer(name,transformer);
    }

    public PowerSystemDevice getPowerSystemDevice(String name) {
        return powerSystemDeviceRepository.getPowerSystemDevice(name);
    }


    public void checkBase() {
        powerSystemDeviceRepository.checkBase();
        return;
    }

//    public String addTransformer(Transformer transformer,String transformerName) {
//
//        return "";
//    }
}
