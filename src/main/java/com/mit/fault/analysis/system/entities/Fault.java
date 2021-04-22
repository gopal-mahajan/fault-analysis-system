package com.mit.fault.analysis.system.entities;


public class Fault {

    String typeOfFault;
    float faultImpedence = 0;

    Fault(String typeOfFault, float faultImpedence) {
        this.typeOfFault = typeOfFault;
        this.faultImpedence = faultImpedence;
    }


}
