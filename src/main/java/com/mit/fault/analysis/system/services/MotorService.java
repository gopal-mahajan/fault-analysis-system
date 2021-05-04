package com.mit.fault.analysis.system.services;


import com.mit.fault.analysis.system.entities.Motor;
import com.mit.fault.analysis.system.repositories.MotorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotorService {

    @Autowired
    MotorRepository motorRepository;

    public String addMotor(String name, Motor motor) {
        return motorRepository.addMotorToMotorMap(name, motor);
    }

    public Motor getMotor(String name) {
        return motorRepository.getMotor(name);
    }

}
