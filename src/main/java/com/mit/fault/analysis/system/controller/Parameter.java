package com.mit.fault.analysis.system.controller;


import com.mit.fault.analysis.system.DTO.ConnectionType;
import com.mit.fault.analysis.system.DTO.PowerSystemType;
import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import com.mit.fault.analysis.system.entities.Transformer;
import com.mit.fault.analysis.system.services.FaultService;
import com.mit.fault.analysis.system.services.PowerSystemDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Parameter {
    @Autowired
    PowerSystemDeviceService powerSystemDeviceService;
    @Autowired
    FaultService faultService;


    @PostMapping("/addPowerSystem")
    String addPowerSystemDevice(@RequestParam("powerSystemName") String powerSystemName,
                                @RequestParam("kvRating") float kvRating, @RequestParam("mvaRating") float mvaRating,
                                @RequestParam("zeroSequenceImpedanceInPerUnit") float zeroSequenceImpedance,
                                @RequestParam("positiveSequenceImpedanceInPerUnit") float positiveSequenceImpedance,
                                @RequestParam("negativeSequenceImpedanceInPerUnit") float negativeSequenceImpedance,
                                @RequestParam("powerSystemType") PowerSystemType powerSystemType,
                                @RequestParam("phaseAngle") float phaseAngle, @RequestParam("isBASE") boolean isBase) {

        PowerSystemDevice powerSystemDevice = new PowerSystemDevice(mvaRating, kvRating, zeroSequenceImpedance, positiveSequenceImpedance,
                    negativeSequenceImpedance, phaseAngle, isBase, powerSystemType);

        return powerSystemDeviceService.addPowerSystemDevice(powerSystemName, powerSystemDevice);

    }

    @PostMapping("/addTransformer")
    String addTransformer(@RequestParam("transformerName") String transformerName,
                          @RequestParam("PrimaryKVRating") float PrimaryKVRating, @RequestParam("SecondaryKVRating") float SecondaryKVRating,
                          @RequestParam("mvaRating") float mvaRating,
                          @RequestParam("zeroSequenceImpedanceInPerUnit") float zeroSequenceImpedance,
                          @RequestParam("positiveSequenceImpedanceInPerUnit") float positiveSequenceImpedance,
                          @RequestParam("negativeSequenceImpedanceInPerUnit") float negativeSequenceImpedance,
                          @RequestParam("phaseAngle") float phaseAngle, @RequestParam("powerSystemType") PowerSystemType powerSystemType,
                          @RequestParam(value = "connectionType", defaultValue = "null") ConnectionType connectionType) {

        Transformer transformer = new Transformer(mvaRating, PrimaryKVRating, SecondaryKVRating, zeroSequenceImpedance
                , positiveSequenceImpedance, negativeSequenceImpedance, phaseAngle, false,powerSystemType, connectionType);
        return powerSystemDeviceService.addTransformer(transformerName, transformer);
    }


    @GetMapping("/changeBase")
    public void checkBase() {
        powerSystemDeviceService.checkBase();
        return;
    }


//    @GetMapping("/getFaultParameter")
//    String faultDetails(@RequestParam("generatorName") String generatorName, @RequestParam("motorName") String motorName,
//                        @RequestParam("faultImpedance") float faultImpedance,
//                        @RequestParam("typeOfFault") FaultType faultType) {
//        PowerSystemDevice generator = powerSystemDeviceService.getPowerSystemDevice(generatorName);
//        PowerSystemDevice motor = powerSystemDeviceService.getPowerSystemDevice(motorName);
//
//        return "Fault Current for the given data is " + faultService.faultParameter(faultType.toString(), faultImpedance, generator, motor) + " per unit.";
//
//    }

    @GetMapping("getDevice")
    public PowerSystemDevice getPower(String name){
        return powerSystemDeviceService.getPowerSystemDevice(name);
    }

}