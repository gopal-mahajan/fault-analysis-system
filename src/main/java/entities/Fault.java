package entities;


public class Fault {

    String typeOfFault;
    float faultImpedence = 0;

    Fault(String typeOfFault) {
        this.typeOfFault = typeOfFault;
    }

    public float lineToGround(float emf, float zeroSequenceImpedence, float positiveSequenceImpedence
            , float negativeSequenceImpedence) {
        float faultCurrent = 0;
        emf = (float) (emf / Math.sqrt(3));
        float equivalentImpedence = zeroSequenceImpedence + positiveSequenceImpedence + negativeSequenceImpedence;
        faultCurrent = 3 * (emf / equivalentImpedence);

        return faultCurrent;
    }

    public double lineToLine(float emf, float positiveSequenceImpedence, float negativeSequenceImpedence) {
        double faultCurrent = 0;
        emf = (float) (emf / Math.sqrt(3));
        float equivalentImpedence = positiveSequenceImpedence + negativeSequenceImpedence + faultImpedence;
        faultCurrent = Math.sqrt(3) * (emf / equivalentImpedence);

        return faultCurrent;
    }


    public double lineToLineToGround(float emf, float positiveSequenceImpedence
            , float negativeSequenceImpedence, float zeroSequenceImpedence) {
        double faultCurrent = 0;
        emf = (float) (emf / Math.sqrt(3));
        float imp = (negativeSequenceImpedence * (zeroSequenceImpedence + (3 * faultImpedence)));
        float imp1 = negativeSequenceImpedence + zeroSequenceImpedence + (3 * faultImpedence);
        float equivalentImpedence = positiveSequenceImpedence + (imp / imp1);
        float positiveCurrent = emf / equivalentImpedence;
        float imp3 = (negativeSequenceImpedence + (zeroSequenceImpedence + negativeSequenceImpedence + (3 * faultImpedence)));
        equivalentImpedence = negativeSequenceImpedence / imp3;

        faultCurrent = 3 * (positiveCurrent * equivalentImpedence);
        return faultCurrent;
    }


}
