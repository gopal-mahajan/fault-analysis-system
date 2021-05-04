package com.mit.fault.analysis.system.repositories;

import com.mit.fault.analysis.system.entities.Motor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MotorRepository {
    private final Map<String, Motor> motorMap = new HashMap<>();

    public Motor getMotor(String MotorName) {
        return motorMap.get(MotorName);
    }

    public String addMotorToMotorMap(String MotorName, Motor Motor) {
        motorMap.put(MotorName, Motor);
        return "Motor Added Successfully";
    }

}
