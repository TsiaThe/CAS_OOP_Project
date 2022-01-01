package com;

import antlr.debug.MessageAdapter;
import com.backend.GameSetup;
import com.backend.Starter;
import com.backend.cards.*;
import com.backend.players.Player;
import com.dto.GameState;
import com.repository.MessageRepository;
import com.repository.UserRepository;
import com.web.Message;
import com.web.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@SpringBootApplication
public class DemoApplication {

   @Autowired
   private UserRepository userRepository;

   public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

   @PostConstruct
   public void afterInit() {
       userRepository.save(new User("Fanis"));
       userRepository.save(new User("Thomas"));
       userRepository.save(new User("Mathieu"));
   }
}