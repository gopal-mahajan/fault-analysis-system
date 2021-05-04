package com.mit.fault.analysis.system.controller;


import com.mit.fault.analysis.system.DTO.FaultType;
import com.mit.fault.analysis.system.DTO.PowerSystem;
import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import com.mit.fault.analysis.system.repositories.FaultService;
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
    String addPowerSystemDevice(@RequestParam("powerSystemName") String powerSystemName, @RequestParam("kvRating") float kvRating,
                              @RequestParam("mvaRating") float mvaRating, @RequestParam("zeroSequenceImpedance") float zeroSequenceImpedance,
                              @RequestParam("positiveSequenceImpedance") float positiveSequenceImpedance,
                              @RequestParam("negativeSequenceImpedance") float negativeSequenceImpedance,
                              @RequestParam("powerSystemType") PowerSystem powerSystem, @RequestParam("/phaseAngle") float phaseAngle
            , @RequestParam("isBASE") boolean isBase) {



        PowerSystemDevice PowerSystemDevice = new PowerSystemDevice(mvaRating, kvRating, zeroSequenceImpedance, positiveSequenceImpedance,
                negativeSequenceImpedance, phaseAngle, isBase, powerSystem);
        return powerSystemDeviceService.addPowerSystemDevice(powerSystemName, PowerSystemDevice);

    }


    @GetMapping("/getFaultParameter")
    String faultDetails(@RequestParam("PowerSystemDeviceName") String PowerSystemDeviceName, @RequestParam("motorName") String motorName,
                        @RequestParam("/faultImpedance") float faultImpedance,
                        @RequestParam("typeOfFault") FaultType faultType) {
        PowerSystemDevice powerSystem = powerSystemDeviceService.getPowerSystemDevice(PowerSystemDeviceName);

        return "Fault Current for the given data is " + faultService.faultParameter(faultType.toString(), faultImpedance, powerSystem);

    }


}