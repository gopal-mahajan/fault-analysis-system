package com.mit.fault.analysis.system.controller;


import com.mit.fault.analysis.system.DTO.FaultType;
import com.mit.fault.analysis.system.entities.Generator;
import com.mit.fault.analysis.system.repositories.FaultService;
import com.mit.fault.analysis.system.services.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Parameter {
    @Autowired
    GeneratorService generatorService;
    @Autowired
    FaultService faultService;


    @PostMapping("/generatorData")
    String addGenerator(@RequestParam("generatorName") String generatorName, @RequestParam("emf") float emf,
                        @RequestParam("zeroSequenceImpedance") float zeroSequenceImpedance,
                        @RequestParam("positiveSequenceImpedance") float positiveSequenceImpedance,
                        @RequestParam("negativeSequenceImpedance") float negativeSequenceImpedance
            , @RequestParam("/phaseAngle") float phaseAngle) {
        Generator generator = new Generator(emf, zeroSequenceImpedance, positiveSequenceImpedance, negativeSequenceImpedance, phaseAngle);
        return generatorService.addGenerator(generatorName, generator);
    }

    @PostMapping("/getFaultParameter")
    String faultDetails(@RequestParam("generatorName") String generatorName, @RequestParam("/faultImpedance") float faultImpedance,
                        @RequestParam("typeOfFault") FaultType faultType) {
        Generator generator = generatorService.getGenerator(generatorName);
        return "Fault Current for the given data is " + faultService.faultParameter(faultType.toString(), faultImpedance, generator);

    }


}