package com.mit.fault.analysis.system.repositories;

import com.mit.fault.analysis.system.entities.Generator;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class GeneratorRepository {
    private final Map<String, Generator> generatorMap = new HashMap<>();

    public Generator getGenerator(String generatorName) {
        return generatorMap.get(generatorName);
    }

    public String addGeneratorToGeneratorMap(String generatorName, Generator generator) {
        generatorMap.put(generatorName, generator);
        return "Generator Added Successfully";
    }

}
