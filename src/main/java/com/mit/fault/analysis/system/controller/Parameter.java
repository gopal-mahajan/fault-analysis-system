package com.mit.fault.analysis.system.controller;

import com.mit.fault.analysis.system.DTO.*;
import com.mit.fault.analysis.system.entities.PowerSystemDevice;
import com.mit.fault.analysis.system.entities.Transformer;
import com.mit.fault.analysis.system.entities.TransmissionLine;
import com.mit.fault.analysis.system.exceptions.BaseAlreadyChanged;
import com.mit.fault.analysis.system.services.FaultService;
import com.mit.fault.analysis.system.services.PowerSystemDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Parameter {
  @Autowired PowerSystemDeviceService powerSystemDeviceService;
  @Autowired FaultService faultService;

  @PostMapping("/addPowerSystem")
  public ResponseEntity<CustomResponse<PowerSystemDevice>> addPowerSystemDevice(
      @RequestParam("powerSystemName") String powerSystemName,
      @RequestParam("kvRating") float kvRating,
      @RequestParam("mvaRating") float mvaRating,
      @RequestParam("zeroSequenceImpedanceInPerUnit") float zeroSequenceImpedance,
      @RequestParam("positiveSequenceImpedanceInPerUnit") float positiveSequenceImpedance,
      @RequestParam("negativeSequenceImpedanceInPerUnit") float negativeSequenceImpedance,
      @RequestParam(value = "powerSystemType") PowerSystemType powerSystemType,
      @RequestParam("isBASE") boolean isBase) {

    PowerSystemDevice powerSystemDevice =
        new PowerSystemDevice(
            mvaRating,
            kvRating,
            zeroSequenceImpedance,
            positiveSequenceImpedance,
            negativeSequenceImpedance,
            isBase,
            powerSystemType);

    String res = powerSystemDeviceService.addPowerSystemDevice(powerSystemName, powerSystemDevice);
    return new ResponseEntity<>(new CustomResponse<>(powerSystemDevice, "Added**"), HttpStatus.OK);
  }

  @PostMapping("/addTransformer")
  public ResponseEntity<CustomResponse<String>> addTransformer(
      @RequestParam("transformerName") String transformerName,
      @RequestParam("PrimaryKVRating") float PrimaryKVRating,
      @RequestParam("SecondaryKVRating") float SecondaryKVRating,
      @RequestParam("mvaRating") float mvaRating,
      @RequestParam("zeroSequenceImpedanceInPerUnit") float zeroSequenceImpedance,
      @RequestParam("positiveSequenceImpedanceInPerUnit") float positiveSequenceImpedance,
      @RequestParam("negativeSequenceImpedanceInPerUnit") float negativeSequenceImpedance,
      @RequestParam(value = "connectionType", defaultValue = "null")
          ConnectionType connectionType) {

    Transformer transformer =
        new Transformer(
            mvaRating,
            PrimaryKVRating,
            SecondaryKVRating,
            zeroSequenceImpedance,
            positiveSequenceImpedance,
            negativeSequenceImpedance,
            false,
            PowerSystemType.TRANSFORMER,
            connectionType);
    String res = powerSystemDeviceService.addTransformer(transformerName, transformer);
    return new ResponseEntity<>(
      new CustomResponse<>(res, ""), HttpStatus.OK);
  }

  @PostMapping("/addTransmissionLine")
  public ResponseEntity<CustomResponse<String>> addTransmissionLine(
      @RequestParam("zeroSequenceImpedanceInPerUnit") float zeroSequenceImpedance,
      @RequestParam("positiveSequenceImpedanceInPerUnit") float positiveSequenceImpedance,
      @RequestParam("negativeSequenceImpedanceInPerUnit") float negativeSequenceImpedance,
      @RequestParam("TransmissionLineName") String name) {
    TransmissionLine transmissionLine =
        new TransmissionLine(
            zeroSequenceImpedance, positiveSequenceImpedance, negativeSequenceImpedance);
    String res = powerSystemDeviceService.addTransmissionLine(name, transmissionLine);
    return new ResponseEntity<>(new CustomResponse<>(res, ""), HttpStatus.OK);
  }

//  @GetMapping("/changeBase")
//  public void checkBase() throws BaseAlreadyChanged {
//    powerSystemDeviceService.checkBase();
//    return;
//  }

  @PostMapping("/getFaultParameter")
  ResponseEntity<CustomResponse<String>> getFaultParameters(
      @RequestParam("typeOfFault") FaultType faultType,
      @RequestParam("positionOfFault") PositionOfFault positionOfFault,
      @RequestParam("faultImpedance") float faultImpedance) throws BaseAlreadyChanged {
    powerSystemDeviceService.checkBase();
    double faultCurrent =
        faultService.getFaultParameters(positionOfFault, faultType, faultImpedance);
    double actualCurrent = faultService.getFinalCurrent(faultCurrent);
    String res= "Fault Current for the given data is : "
        + faultCurrent
        + " per unit /n"
        + " Actual Current is : "
        + actualCurrent
        + " amp";
    return new ResponseEntity<>(new CustomResponse<>(res," "),HttpStatus.OK);
  }

  @GetMapping("getDevice")
  public PowerSystemDevice getPower(String name) {
    return powerSystemDeviceService.getPowerSystemDevice(name);
  }

  @GetMapping("getTransmissionLine")
  public TransmissionLine getTransmissionLine(String name) {
    return powerSystemDeviceService.getTransmissionLine(name);
  }
}
