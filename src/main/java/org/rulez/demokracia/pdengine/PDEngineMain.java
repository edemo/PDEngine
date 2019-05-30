package org.rulez.demokracia.pdengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class PDEngineMain {

  public static void main(final String[] args) {
    SpringApplication.run(PDEngineMain.class, args);
  }

}
