package controller;

public class parameter {


}


class Generator {
     float emf;
    float zeroSequenceImpedence;
    float positiveSequenceImpedence;
    float negativeSequenceImpedence;
    int phaseAngle=0;

    Generator(float emf,float zeroSequenceImpedence,float positiveSequenceImpedence,float negativeSequenceImpedence){
        this.emf=emf;
        this.zeroSequenceImpedence=zeroSequenceImpedence;
        this.positiveSequenceImpedence=positiveSequenceImpedence;
        this.negativeSequenceImpedence =negativeSequenceImpedence;

    }


}
class Fault{
    String typeOfFault;
    int faultImpedence=0;

    Fault(String typeOfFault){
        this.typeOfFault=typeOfFault;
    }

    public float lineToLine(float emf,float zeroSequenceImpedence,float positiveSequenceImpedence,float negativeSequenceImpedence){
        float faultCurrent=0;
        emf= (float) (emf/Math.sqrt(3));
        float equivalentImpedence=zeroSequenceImpedence+positiveSequenceImpedence+negativeSequenceImpedence;
        faultCurrent=emf/equivalentImpedence;

        return faultCurrent;
    }


}