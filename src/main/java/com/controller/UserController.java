package com.controller;

import com.dto.GameState;
import com.repository.UserRepository;
import com.web.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping
public class UserController {

    private final UserRepository userRepository;
    private final GameState gameState;

    @Autowired
    public UserController(UserRepository userRepository, GameState gameState) {
        this.userRepository = userRepository;
        this.gameState= gameState;
    }

    @GetMapping("/")
    public String loginPage(Model model) {
        if (userRepository.count()==4) return "SupportPage";
        model.addAttribute("users", userRepository.findAll());
        return "LoginPage";
    }

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "AddUser";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        for (User u:userRepository.findAll()){
         if (u.getName().equals(user.getName())) return "AddUser";
        }
        if (user.getName()=="") return  "AddUser";
        if (userRepository.count()==4){
            return "SupportPage";
        }
        User newUser = userRepository.save(user);
        gameState.assignPlayerId(user.getId());
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/action/"+newUser.getId() +"/"+gameState.getMainPlayer().getId();
    }

}
