package com.mit.fault.analysis.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Application  {
//  @Override
//  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//    return builder.sources(Application.class);
//  }
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
