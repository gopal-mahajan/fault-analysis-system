package com.mit.fault.analysis.system.entities;

import lombok.Data;

@Data
public class TransmissionLine {
  float zeroSequenceImpedance;
  float positiveSequenceImpedance;
  float negativeSequenceImpedance;

  public TransmissionLine(
      float zeroSequenceImpedance,
      float positiveSequenceImpedance,
      float negativeSequenceImpedance) {
    this.zeroSequenceImpedance = zeroSequenceImpedance;
    this.positiveSequenceImpedance = positiveSequenceImpedance;
    this.negativeSequenceImpedance = negativeSequenceImpedance;
  }
}
