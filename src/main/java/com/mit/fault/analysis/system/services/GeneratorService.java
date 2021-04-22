package com.mit.fault.analysis.system.services;


import com.mit.fault.analysis.system.entities.Generator;
import com.mit.fault.analysis.system.repositories.GeneratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneratorService {

    @Autowired
    GeneratorRepository generatorRepository;

    public String addGenerator(String name, Generator generator) {
        return generatorRepository.addGeneratorToGeneratorMap(name, generator);
    }

    public Generator getGenerator(String name) {
        return generatorRepository.getGenerator(name);
    }

}
