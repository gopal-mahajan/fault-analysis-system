package com.mit.fault.analysis.system.entities;

import com.mit.fault.analysis.system.DTO.PowerSystemType;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PowerSystemDevice {

  float kvRating;
  float newKVRating;
  float mvaRating;
  float zeroSequenceImpedance;
  float positiveSequenceImpedance;
  float negativeSequenceImpedance;
  float phaseAngle = 0;
  boolean isBase;
  PowerSystemType powerSystemType;

  public PowerSystemDevice(
      float mvaRating,
      float kvRating,
      float zeroSequenceImpedance,
      float positiveSequenceImpedance,
      float negativeSequenceImpedance,
      float phaseAngle,
      boolean isBase,
      PowerSystemType powerSystemType) {
    this.isBase = isBase;
    this.kvRating = kvRating;
    this.powerSystemType = powerSystemType;
    this.mvaRating = mvaRating;
    this.zeroSequenceImpedance = zeroSequenceImpedance;
    this.positiveSequenceImpedance = positiveSequenceImpedance;
    this.negativeSequenceImpedance = negativeSequenceImpedance;
    this.phaseAngle = phaseAngle;
    this.newKVRating = kvRating;
  }

  public PowerSystemDevice(
      float zeroSequenceImpedance,
      float positiveSequenceImpedance,
      float negativeSequenceImpedance,
      PowerSystemType powerSystemType) {
    this.zeroSequenceImpedance = zeroSequenceImpedance;
    this.positiveSequenceImpedance = positiveSequenceImpedance;
    this.negativeSequenceImpedance = negativeSequenceImpedance;
    this.powerSystemType = powerSystemType;
  }
}
