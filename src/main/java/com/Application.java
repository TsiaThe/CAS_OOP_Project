package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
@SpringBootApplication
public class Application {

   public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

   @PostConstruct
   public void afterInit() {
       // Actions to be performed after spring initialization.
   }
}