package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import javax.annotation.PostConstruct;

/**
 * Starting point og the application.
 * The @PostContruct annotation was not used at
 * the end but left in as it might be useful for
 * future implementations.
 * @author Theofanis Tsiantas
 * @version  2021.12.29 - version 3
 */
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