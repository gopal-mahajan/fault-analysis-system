package com.mit.fault.analysis.system.repositories;

import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import com.mit.fault.analysis.system.entities.Transformer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class FaultRepository {
    @Autowired
    PowerSystemDeviceRepository powerSystemDeviceRepository;

    private final Map<String, PowerSystemDevice> powerSystemDeviceMap = powerSystemDeviceRepository.getPowerSystemDeviceMap();
    private final Map<String, Transformer> transformerMap = powerSystemDeviceRepository.getTransformerMap();

    public Map<String,PowerSystemDevice> getPowerSystemDeviceMap(){
        return powerSystemDeviceMap;
    }
    public Map<String,Transformer> getTransformerMap(){
        return transformerMap;
    }

}
