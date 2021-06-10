package com.mit.fault.analysis.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
  @RequestMapping("/")
  public String get(){
    return "homepage.html";
  }
}
