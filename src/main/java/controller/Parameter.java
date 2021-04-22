package controller;

import entities.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.GeneratorService;

@RestController
public class Parameter {
    @Autowired
    GeneratorService generatorService;



    @PostMapping("/generatorData")
    String addGenerator(@RequestParam("generatorName") String generatorName,@RequestParam("emf") float emf,
                        @RequestParam("zeroSequenceImpedance") float zeroSequenceImpedance,
                        @RequestParam("positiveSequenceImpedance") float positiveSequenceImpedance,
                        @RequestParam("negativeSequenceImpedance") float negativeSequenceImpedance
                        ,@RequestParam("/phaseAngle") float phaseAngle)
    {
        Generator generator=new Generator(emf, zeroSequenceImpedance, positiveSequenceImpedance, negativeSequenceImpedance, phaseAngle);
        return generatorService.addGenerator(generatorName,generator);
    }





    
    
}