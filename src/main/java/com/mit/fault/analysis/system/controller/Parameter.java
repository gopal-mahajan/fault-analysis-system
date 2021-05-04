package com.mit.fault.analysis.system.controller;


import com.mit.fault.analysis.system.DTO.FaultType;
import com.mit.fault.analysis.system.entities.Generator;
import com.mit.fault.analysis.system.entities.Motor;
import com.mit.fault.analysis.system.repositories.FaultService;
import com.mit.fault.analysis.system.services.GeneratorService;
import com.mit.fault.analysis.system.services.MotorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Parameter {
    @Autowired
    GeneratorService generatorService;
    @Autowired
    FaultService faultService;
    @Autowired
    MotorService motorService;

    @PostMapping("/addGenerator")
    String addGenerator(@RequestParam("generatorName") String generatorName, @RequestParam("emf") float emf,
                        @RequestParam("zeroSequenceImpedance") float zeroSequenceImpedance,
                        @RequestParam("positiveSequenceImpedance") float positiveSequenceImpedance,
                        @RequestParam("negativeSequenceImpedance") float negativeSequenceImpedance
            , @RequestParam("/phaseAngle") float phaseAngle) {
        Generator generator = new Generator(emf, zeroSequenceImpedance, positiveSequenceImpedance, negativeSequenceImpedance, phaseAngle);
        return generatorService.addGenerator(generatorName, generator);
    }

    @PostMapping("/addMotor")
    String addMotor(@RequestParam("motorName") String motorName, @RequestParam("emf") float emf,
                    @RequestParam("zeroSequenceImpedance") float zeroSequenceImpedance,
                    @RequestParam("positiveSequenceImpedance") float positiveSequenceImpedance,
                    @RequestParam("negativeSequenceImpedance") float negativeSequenceImpedance
            , @RequestParam("/phaseAngle") float phaseAngle) {
        Motor motor = new Motor(emf, zeroSequenceImpedance, positiveSequenceImpedance, negativeSequenceImpedance, phaseAngle);
        return motorService.addMotor(motorName, motor);
    }


    @GetMapping("/getFaultParameter")
    String faultDetails(@RequestParam("generatorName") String generatorName, @RequestParam("motorName") String motorName,
                        @RequestParam("/faultImpedance") float faultImpedance,
                        @RequestParam("typeOfFault") FaultType faultType) {
        Generator generator = generatorService.getGenerator(generatorName);
        Motor motor = motorService.getMotor(motorName);
        return "Fault Current for the given data is " + faultService.faultParameter(faultType.toString(), faultImpedance, generator);

    }


}