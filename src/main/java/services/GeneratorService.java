package services;


import entities.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import repositories.GeneratorRepository;

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
